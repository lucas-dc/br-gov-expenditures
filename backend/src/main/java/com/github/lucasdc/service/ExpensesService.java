package com.github.lucasdc.service;

import com.github.lucasdc.client.GovAPIClient;
import com.github.lucasdc.dto.expenses.ExpensesByOrganDTO;
import com.github.lucasdc.dto.expenses.ExpensesByOrganResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ExpensesService {

    private final GovAPIClient client;

    public ExpensesService(GovAPIClient client) {
        this.client = client;
    }

    public List<ExpensesByOrganResponseDTO> getExpensesByOrganAndYear(String organName, Long year, Long page) {
        Map<String, Object> params = Map.of(
                "orgao", organName,
                "ano", year,
                "pagina", page
        );

        List<ExpensesByOrganDTO> expensesByOrgan = client.get("/despesas/por-orgao", params, ExpensesByOrganDTO.class);

        return expensesByOrgan.stream()
                .map(e -> new ExpensesByOrganResponseDTO(e.getOrgan(), e.getPaidValue()))
                .toList();
    }
}
