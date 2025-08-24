package com.github.lucasdc.controller;

import com.github.lucasdc.BRGovExpendituresApplication;
import com.github.lucasdc.client.GovAPIClient;
import com.github.lucasdc.dto.expenditures.ExpendituresByOrganDTO;
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

@SpringBootTest(classes = BRGovExpendituresApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ExpendituresControllerIT {

    private final String organCode = "63000";
    private final String year = "2025";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GovAPIClient client;

    @Test
    void shouldReturnExpenditureListByOrganAndYear() throws Exception {
        List<ExpendituresByOrganDTO> mockResponse = List.of(new ExpendituresByOrganDTO("Lorem Ipsum", BigDecimal.valueOf(1234.56)));

        when(client.get(any(), any(), eq(ExpendituresByOrganDTO.class)))
                .thenReturn(mockResponse);

        mockMvc.perform(get("/api/expenditure/organ/"+organCode+"/"+year)
                        .param("page", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].organ").value("Lorem Ipsum"))
                .andExpect(jsonPath("$[0].paid").value(BigDecimal.valueOf(1234.56)));
    }
}
