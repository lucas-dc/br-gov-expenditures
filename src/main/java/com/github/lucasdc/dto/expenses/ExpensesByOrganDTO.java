package com.github.lucasdc.dto.expenses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.lucasdc.util.LocalizedBigDecimalDeserializer;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExpensesByOrganDTO {
    @JsonProperty("orgao")
    private String organ;

    @JsonProperty("pago")
    @JsonDeserialize(using = LocalizedBigDecimalDeserializer.class)
    private BigDecimal paidValue;

    public String getOrgan() {
        return organ;
    }

    public void setOrgan(String organ) {
        this.organ = organ;
    }

    public BigDecimal getPaidValue() {
        return paidValue;
    }

    public void setPaidValue(BigDecimal paidValue) {
        this.paidValue = paidValue;
    }
}
