package com.github.lucasdc.service;

import com.github.lucasdc.client.GovAPIClient;
import com.github.lucasdc.dto.expenses.ExpensesByOrganDTO;
import com.github.lucasdc.dto.expenses.ExpensesByOrganRequestDTO;
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

    public List<ExpensesByOrganResponseDTO> getExpensesByOrgan(ExpensesByOrganRequestDTO requestDTO) {
        Map<String, String> params = Map.of(
                "ano", requestDTO.getYear(),
                "orgao", requestDTO.getOrgan(),
                "pagina", String.valueOf(requestDTO.getPage())
        );

        List<ExpensesByOrganDTO> expensesByOrgan = client.get("/despesas/por-orgao", params, ExpensesByOrganDTO.class);

        return expensesByOrgan.stream()
                .map(e -> new ExpensesByOrganResponseDTO(e.getOrgan(), e.getPaidValue()))
                .toList();
    }
}
