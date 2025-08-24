package com.github.lucasdc.controller;

import com.github.lucasdc.dto.expenditures.ExpendituresResponseDTO;
import com.github.lucasdc.service.ExpendituresService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenditures")
public class ExpendituresController {

    private final ExpendituresService service;

    public ExpendituresController(ExpendituresService service) {
        this.service = service;
    }

    @GetMapping("/{year}")
    public ResponseEntity<List<ExpendituresResponseDTO>> listAllExpendituresByYear(
            @PathVariable("year") Long year,
            @RequestParam("page") Long page) {
        List<ExpendituresResponseDTO> result = service.getExpendituresByYear(year, page);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/organ/{code}/{year}")
    public ResponseEntity<List<ExpendituresResponseDTO>> listAllExpendituresByOrganAndYear(
            @PathVariable("code") String code,
            @PathVariable("year") Long year,
            @RequestParam("page") Long page) {
        List<ExpendituresResponseDTO> result = service.getExpendituresByOrganCodeAndYear(code, year, page);
        return ResponseEntity.ok(result);
    }
}
