package com.github.lucasdc.controller;

import com.github.lucasdc.entity.Branch;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ExpendituresControllerIT {

    private final String organCode = "63000";
    private final Long year = 2025L;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnExpendituresByOrganAndYear() throws Exception {
        System.out.println("Test: "+Branch.EXECUTIVE.getName());
        System.out.println("Test2: "+Branch.EXECUTIVE.toString());

        mockMvc.perform(get("/api/expenditures/organ/"+organCode+"/"+year)
                        .param("page", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].organCode").value("63000"))
                .andExpect(jsonPath("$[0].organName").value("Advocacia-Geral da União - Unidades com vínculo direto"))
                .andExpect(jsonPath("$[0].paid").value(BigDecimal.valueOf(2557157520.42)));
    }
}
