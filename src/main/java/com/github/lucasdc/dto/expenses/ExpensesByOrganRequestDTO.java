package com.github.lucasdc.dto.expenses;

public class ExpensesByOrganRequestDTO {

    private String year;
    private String organ;
    private int page = 1;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getOrgan() {
        return organ;
    }

    public void setOrgan(String organ) {
        this.organ = organ;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
