package com.github.lucasdc.entity;

import jakarta.persistence.*;

@Entity
public class Organ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    @Enumerated(EnumType.STRING)
    private Branch branch;

    public Organ() {}

    public Organ(String code, String name, Branch branch) {
        this.code = code;
        this.name = name;
        this.branch = branch;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
}
