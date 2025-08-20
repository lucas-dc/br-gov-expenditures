package com.github.lucasdc.service;

import com.github.lucasdc.cache.LettuceCache;
import com.github.lucasdc.client.GovAPIClient;
import com.github.lucasdc.dto.expenses.ExpensesByOrganDTO;
import com.github.lucasdc.dto.expenses.ExpensesByOrganResponseDTO;
import com.github.lucasdc.entity.Organ;
import com.github.lucasdc.repository.OrganRepository;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ExpensesService {
    private final int REQUESTS_PER_MINUTE_LIMIT = 400;
    private final String CACHE_NAME = "expensesCache";

    private final GovAPIClient client;
    private final OrganRepository organRepository;
    private final CacheManager cacheManager;

    public ExpensesService(GovAPIClient client, OrganRepository organRepository, CacheManager cacheManager) {
        this.client = client;
        this.organRepository = organRepository;
        this.cacheManager = cacheManager;
    }

    public List<ExpensesByOrganResponseDTO> getExpensesByYear(Long year, Long page) {
        List<ExpensesByOrganResponseDTO> expensesByYear = new ArrayList<>();
        List<Organ> organs = organRepository.findAll();
        boolean shouldPreventAPIBlock = organs.size() > REQUESTS_PER_MINUTE_LIMIT;

        for(Organ organ : organs) {
            List<ExpensesByOrganResponseDTO> expense = this.getExpensesByOrganCodeAndYear(organ.getCode(), year, page, shouldPreventAPIBlock);

            if(expense != null && !expense.isEmpty()) {
                expense.get(0).setBranch(organ.getBranch());
                expensesByYear.add(expense.get(0));
            }
        }

        return expensesByYear;
    }

    public List<ExpensesByOrganResponseDTO> getExpensesByOrganCodeAndYear(String organCode, Long year, Long page) {
        return this.getExpensesByOrganCodeAndYear(organCode, year, page, false);
    }

    public List<ExpensesByOrganResponseDTO> getExpensesByOrganCodeAndYear(String organCode, Long year, Long page, boolean shouldPreventAPIBlock) {
        LettuceCache cache = (LettuceCache) cacheManager.getCache(CACHE_NAME);
        String cacheKey = organCode + ":" + year;

        if(cache != null) {
            List<ExpensesByOrganResponseDTO> cachedValue = cache.get(cacheKey, List.class, ExpensesByOrganResponseDTO.class);

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

        List<ExpensesByOrganDTO> expensesByOrgan = client.get("/despesas/por-orgao", params, ExpensesByOrganDTO.class);
        List<ExpensesByOrganResponseDTO> responseDTO = expensesByOrgan.stream()
                .map(e -> {
                    Optional<Organ> organ = organRepository.findByCode(organCode);
                    return new ExpensesByOrganResponseDTO(organCode, e.getOrgan(), e.getPaidValue(), organ.get().getBranch());
                })
                .toList();

        cache.put(cacheKey, responseDTO);

        return responseDTO;
    }

    // Apply a small delay to prevent being blocked due to max request limit per minute
    private void preventAPIBlock() {
        try {
            Thread.sleep(150);
        } catch (InterruptedException ignoredEx) {}
    }
}
