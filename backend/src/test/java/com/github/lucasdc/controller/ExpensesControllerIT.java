package com.github.lucasdc.controller;

import com.github.lucasdc.client.GovAPIClient;
import com.github.lucasdc.dto.expenses.ExpensesByOrganDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = com.github.lucasdc.BRGovExpensesApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ExpensesControllerIT {

    private final String organCode = "63000";
    private final String year = "2025";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GovAPIClient client;

    @Test
    void shouldReturnExpensesListByOrganAndYear() throws Exception {
        List<ExpensesByOrganDTO> mockResponse = List.of(new ExpensesByOrganDTO("Lorem Ipsum", BigDecimal.valueOf(1234.56)));

        when(client.get(any(), any(), eq(ExpensesByOrganDTO.class)))
                .thenReturn(mockResponse);

        mockMvc.perform(get("/api/expenses/organ/"+organCode+"/"+year)
                        .param("page", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].organ").value("Lorem Ipsum"))
                .andExpect(jsonPath("$[0].paid").value(BigDecimal.valueOf(1234.56)));
    }
}
