package com.github.lucasdc.dto.expenses;

import com.github.lucasdc.entity.Branch;

import java.io.Serializable;
import java.math.BigDecimal;

public class ExpensesByOrganResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String organCode;
    private String organName;
    private BigDecimal  paid;
    private Branch branch;

    public ExpensesByOrganResponseDTO() {}

    public ExpensesByOrganResponseDTO(String organCode, String organName, BigDecimal paid, Branch branch) {
        this.organCode = organCode;
        this.organName = organName;
        this.paid = paid;
        this.branch = branch;
    }

    public String getOrganCode() {
        return organCode;
    }

    public void setOrganCode(String organCode) {
        this.organCode = organCode;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public BigDecimal getPaid() {
        return paid;
    }

    public void setPaid(BigDecimal paid) {
        this.paid = paid;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
}
