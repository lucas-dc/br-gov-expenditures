package com.github.lucasdc.controller;

import com.github.lucasdc.dto.expenditures.ExpendituresByOrganResponseDTO;
import com.github.lucasdc.service.ExpendituresService;
import com.github.lucasdc.util.SIAFIOrganInitializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ExpendituresControllerTest {

    private final String organCode = "63000";
    private final Long year = 2025L;

    private MockMvc mockMvc;

    @Mock
    private ExpendituresService expendituresService;

    @InjectMocks
    private ExpendituresController expendituresController;

    @MockitoBean
    private SIAFIOrganInitializer siafiOrganInitializer; // prevent database population

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(expendituresController).build();
    }

    @Test
    void shouldReturnExpendituresByYear() throws Exception {
        ExpendituresByOrganResponseDTO mockResponse = new ExpendituresByOrganResponseDTO();
        mockResponse.setOrganCode("63000");
        mockResponse.setPaid(BigDecimal.valueOf(1234.56));

        when(expendituresService.getExpendituresByYear(eq(2025L), eq(1L))).thenReturn(Arrays.asList(mockResponse));

        mockMvc.perform(get("/api/expenditures/"+year)
                        .param("page", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].organCode").value("63000"))
                .andExpect(jsonPath("$[0].paid").value(1234.56));
    }

    @Test
    void shouldReturnExpendituresByOrganAndYear() throws Exception {
        ExpendituresByOrganResponseDTO mockResponse = new ExpendituresByOrganResponseDTO();
        mockResponse.setOrganCode("63000");
        mockResponse.setPaid(BigDecimal.valueOf(1234.56));

        when(expendituresService.getExpendituresByOrganCodeAndYear(eq("63000"), eq(2025L), eq(1L)))
                .thenReturn(Arrays.asList(mockResponse));

        mockMvc.perform(get("/api/expenditures/organ/"+organCode+"/"+year)
                        .param("page", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].organCode").value("63000"))
                .andExpect(jsonPath("$[0].paid").value(1234.56));
    }
}

