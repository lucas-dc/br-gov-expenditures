package com.github.lucasdc.controller;

import com.github.lucasdc.dto.expenses.ExpensesByOrganResponseDTO;
import com.github.lucasdc.service.ExpensesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpensesController {

    private final ExpensesService service;

    public ExpensesController(ExpensesService service) {
        this.service = service;
    }

    @GetMapping("/{year}")
    public ResponseEntity<List<ExpensesByOrganResponseDTO>> listAllExpensesByYear(
            @PathVariable("year") Long year,
            @RequestParam("page") Long page) {
        List<ExpensesByOrganResponseDTO> result = service.getExpensesByYear(year, page);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/organ/{code}/{year}")
    public ResponseEntity<List<ExpensesByOrganResponseDTO>> listAllExpensesByOrganAndYear(
            @PathVariable("code") String code,
            @PathVariable("year") Long year,
            @RequestParam("page") Long page) {
        List<ExpensesByOrganResponseDTO> result = service.getExpensesByOrganCodeAndYear(code, year, page);
        return ResponseEntity.ok(result);
    }
}
