package com.github.lucasdc.dto.expenditures;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.lucasdc.util.LocalizedBigDecimalDeserializer;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExpendituresByOrganDTO {
    @JsonProperty("orgao")
    private String organ;

    @JsonProperty("pago")
    @JsonDeserialize(using = LocalizedBigDecimalDeserializer.class)
    private BigDecimal paidValue;

    public ExpendituresByOrganDTO() {
    }

    public ExpendituresByOrganDTO(String organName, BigDecimal paidValue) {
        this.organ = organName;
        this.paidValue = paidValue;
    }

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
