package com.github.lucasdc.controller;

import com.github.lucasdc.dto.expenditures.ExpendituresByOrganResponseDTO;
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
    public ResponseEntity<List<ExpendituresByOrganResponseDTO>> listAllExpendituresByYear(
            @PathVariable("year") Long year,
            @RequestParam("page") Long page) {
        List<ExpendituresByOrganResponseDTO> result = service.getExpendituresByYear(year, page);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/organ/{code}/{year}")
    public ResponseEntity<List<ExpendituresByOrganResponseDTO>> listAllExpendituresByOrganAndYear(
            @PathVariable("code") String code,
            @PathVariable("year") Long year,
            @RequestParam("page") Long page) {
        List<ExpendituresByOrganResponseDTO> result = service.getExpendituresByOrganCodeAndYear(code, year, page);
        return ResponseEntity.ok(result);
    }
}
