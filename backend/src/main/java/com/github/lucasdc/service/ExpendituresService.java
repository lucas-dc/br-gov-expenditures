package com.github.lucasdc.service;

import com.github.lucasdc.cache.LettuceCache;
import com.github.lucasdc.client.GovAPIClient;
import com.github.lucasdc.dto.expenditures.ExpendituresByOrganDTO;
import com.github.lucasdc.dto.expenditures.ExpendituresResponseDTO;
import com.github.lucasdc.entity.Organ;
import com.github.lucasdc.repository.OrganRepository;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ExpendituresService {
    private final int REQUESTS_PER_MINUTE_LIMIT = 400;
    private final String CACHE_NAME = "expendituresCache";

    private final GovAPIClient client;
    private final OrganRepository organRepository;
    private final CacheManager cacheManager;

    public ExpendituresService(GovAPIClient client, OrganRepository organRepository, CacheManager cacheManager) {
        this.client = client;
        this.organRepository = organRepository;
        this.cacheManager = cacheManager;
    }

    public List<ExpendituresResponseDTO> getExpendituresByYear(Long year, Long page) {
        List<ExpendituresResponseDTO> expendituresByYear = new ArrayList<>();
        List<Organ> organs = organRepository.findAll();
        boolean shouldPreventAPIBlock = organs.size() > REQUESTS_PER_MINUTE_LIMIT;

        for(Organ organ : organs) {
            List<ExpendituresResponseDTO> expense = this.getExpendituresByOrganCodeAndYear(organ.getCode(), year, page, shouldPreventAPIBlock);

            if(expense != null && !expense.isEmpty()) {
                expense.get(0).setBranch(organ.getBranch());
                expendituresByYear.add(expense.get(0));
            }
        }

        return expendituresByYear;
    }

    public List<ExpendituresResponseDTO> getExpendituresByOrganCodeAndYear(String organCode, Long year, Long page) {
        return this.getExpendituresByOrganCodeAndYear(organCode, year, page, false);
    }

    public List<ExpendituresResponseDTO> getExpendituresByOrganCodeAndYear(String organCode, Long year, Long page, boolean shouldPreventAPIBlock) {
        LettuceCache cache = (LettuceCache) cacheManager.getCache(CACHE_NAME);
        String cacheKey = organCode + ":" + year;

        if(cache != null) {
            List<ExpendituresResponseDTO> cachedValue = cache.get(cacheKey, List.class, ExpendituresResponseDTO.class);

            if (cachedValue != null) {
                return cachedValue;
            }
        }

        if(shouldPreventAPIBlock) {
            preventAPIBlock();
        }

        Map<String, Object> params = Map.of(
                "orgao", organCode,
                "ano", year,
                "pagina", page
        );

        List<ExpendituresByOrganDTO> expendituresByOrgan = client.get("/despesas/por-orgao", params, ExpendituresByOrganDTO.class);
        List<ExpendituresResponseDTO> responseDTO = expendituresByOrgan.stream()
                .map(e -> {
                    Optional<Organ> organ = organRepository.findByCode(organCode);
                    return new ExpendituresResponseDTO(organCode, e.getOrgan(), e.getPaidValue(), organ.get().getBranch());
                })
                .toList();

        if(cache != null) {
            cache.put(cacheKey, responseDTO);
        }

        return responseDTO;
    }

    // Apply a small delay to prevent being blocked due to max request limit per minute
    private void preventAPIBlock() {
        try {
            Thread.sleep(150);
        } catch (InterruptedException ignoredEx) {}
    }
}
