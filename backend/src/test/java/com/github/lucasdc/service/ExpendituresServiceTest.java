package com.github.lucasdc.service;

import com.github.lucasdc.client.GovAPIClient;
import com.github.lucasdc.dto.expenditures.ExpendituresByOrganDTO;
import com.github.lucasdc.dto.expenditures.ExpendituresByOrganResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExpendituresServiceTest {

    @InjectMocks
    private ExpendituresService service;

    @Mock
    private GovAPIClient client;

    @Test
    void shouldReturnExpenditureByOrganAndYear() {
        String organName = "Lorem Ipsum";
        Long year = 2025L;
        Long page = 1L;

        List<ExpendituresByOrganDTO> mockResponse = List.of(new ExpendituresByOrganDTO(organName, BigDecimal.valueOf(1234.56)));

        when(client.get(any(), any(), eq(ExpendituresByOrganDTO.class)))
                .thenReturn(mockResponse);

        List<ExpendituresByOrganResponseDTO> result = service.getExpendituresByOrganCodeAndYear(organName, year, page);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Lorem Ipsum", result.get(0).getOrganName());
        assertEquals(BigDecimal.valueOf(1234.56), result.get(0).getPaid());
    }
}
