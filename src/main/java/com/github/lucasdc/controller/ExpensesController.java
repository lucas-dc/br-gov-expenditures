package com.github.lucasdc.controller;

import com.github.lucasdc.dto.expenses.ExpensesByOrganRequestDTO;
import com.github.lucasdc.dto.expenses.ExpensesByOrganResponseDTO;
import com.github.lucasdc.service.ExpensesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpensesController {

    private final ExpensesService service;

    public ExpensesController(ExpensesService service) {
        this.service = service;
    }

    @GetMapping("/organ")
    public ResponseEntity<List<ExpensesByOrganResponseDTO>> listAllExpensesByOrgan(
            @ModelAttribute ExpensesByOrganRequestDTO requestDTO) {
        List<ExpensesByOrganResponseDTO> result = service.getExpensesByOrgan(requestDTO);
        return ResponseEntity.ok(result);
    }
}
