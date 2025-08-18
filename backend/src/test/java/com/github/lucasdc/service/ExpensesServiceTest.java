package com.github.lucasdc.service;

import com.github.lucasdc.client.GovAPIClient;
import com.github.lucasdc.dto.expenses.ExpensesByOrganDTO;
import com.github.lucasdc.dto.expenses.ExpensesByOrganResponseDTO;
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
public class ExpensesServiceTest {

    @InjectMocks
    private ExpensesService service;

    @Mock
    private GovAPIClient client;

    @Test
    void shouldReturnExpensesByOrganAndYear() {
        String organName = "Lorem Ipsum";
        Long year = 2025L;
        Long page = 1L;

        List<ExpensesByOrganDTO> mockResponse = List.of(new ExpensesByOrganDTO(organName, BigDecimal.valueOf(1234.56)));

        when(client.get(any(), any(), eq(ExpensesByOrganDTO.class)))
                .thenReturn(mockResponse);

        List<ExpensesByOrganResponseDTO> result = service.getExpensesByOrganCodeAndYear(organName, year, page);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Lorem Ipsum", result.get(0).getOrgan());
        assertEquals(BigDecimal.valueOf(1234.56), result.get(0).getPaid());
    }
}
