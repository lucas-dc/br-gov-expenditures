package com.github.lucasdc.dto.expenses;

import java.io.Serializable;
import java.math.BigDecimal;

public class ExpensesByOrganResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String organ;
    private BigDecimal  paid;

    public ExpensesByOrganResponseDTO() {}

    public ExpensesByOrganResponseDTO(String organ, BigDecimal paid) {
        this.organ = organ;
        this.paid = paid;
    }

    public String getOrgan() {
        return organ;
    }

    public void setOrgan(String organ) {
        this.organ = organ;
    }

    public BigDecimal getPaid() {
        return paid;
    }

    public void setPaid(BigDecimal paid) {
        this.paid = paid;
    }
}
