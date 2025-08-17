package com.github.lucasdc.service;

import com.github.lucasdc.client.GovAPIClient;
import com.github.lucasdc.dto.expenses.ExpensesByOrganDTO;
import com.github.lucasdc.dto.expenses.ExpensesByOrganResponseDTO;
import com.github.lucasdc.entity.Organ;
import com.github.lucasdc.repository.OrganRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ExpensesService {

    private final GovAPIClient client;
    private final OrganRepository repository;

    public ExpensesService(GovAPIClient client, OrganRepository repository) {
        this.client = client;
        this.repository = repository;
    }

    public List<ExpensesByOrganResponseDTO> getExpensesByYear(Long year, Long page) {
        List<ExpensesByOrganDTO> expensesByYear = new ArrayList<>();

        for(Organ organ : repository.findAll()) {
            Map<String, Object> params = Map.of(
                    "orgao", organ.getCode(),
                    "ano", year,
                    "pagina", page
            );

            List<ExpensesByOrganDTO> expense = client.get("/despesas/por-orgao", params, ExpensesByOrganDTO.class);

            if(expense != null && !expense.isEmpty()) {
                expensesByYear.add(expense.get(0));
            }
        }

        return expensesByYear.stream()
                .map(e -> new ExpensesByOrganResponseDTO(e.getOrgan(), e.getPaidValue()))
                .toList();
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
