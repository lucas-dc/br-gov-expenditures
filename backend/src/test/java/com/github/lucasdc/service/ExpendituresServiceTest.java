package com.github.lucasdc.service;

import com.github.lucasdc.client.GovAPIClient;
import com.github.lucasdc.dto.expenditures.ExpendituresByOrganDTO;
import com.github.lucasdc.dto.expenditures.ExpendituresByOrganResponseDTO;
import com.github.lucasdc.entity.Branch;
import com.github.lucasdc.entity.Organ;
import com.github.lucasdc.repository.OrganRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.CacheManager;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExpendituresServiceTest {

    private final String CACHE_NAME = "expendituresCache";

    @InjectMocks
    private ExpendituresService service;

    @Mock
    private GovAPIClient client;

    @Mock
    private OrganRepository organRepository;

    @Mock
    private CacheManager cacheManager;

    @Test
    void shouldReturnExpenditureByOrganAndYear() {
        String organCode = "65000";
        Long year = 2025L;
        Long page = 1L;

        List<ExpendituresByOrganDTO> mockResponse = List.of(new ExpendituresByOrganDTO(organCode, BigDecimal.valueOf(1234.56)));

        when(client.get(any(), any(), eq(ExpendituresByOrganDTO.class)))
                .thenReturn(mockResponse);

        when(cacheManager.getCache(CACHE_NAME)).thenReturn(null);

        when(organRepository.findByCode(organCode)).thenReturn(Optional.of(new Organ(organCode, "Lorem Ipsum", Branch.EXECUTIVE)));

        List<ExpendituresByOrganResponseDTO> result = service.getExpendituresByOrganCodeAndYear(organCode, year, page);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("65000", result.get(0).getOrganName());
        assertEquals(BigDecimal.valueOf(1234.56), result.get(0).getPaid());
    }

    private void a() {

    }
}
