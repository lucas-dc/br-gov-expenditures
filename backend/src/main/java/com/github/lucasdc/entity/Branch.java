package com.github.lucasdc.entity;

public enum Branch {
    LEGISLATIVE("LEGISLATIVO"),
    EXECUTIVE("EXECUTIVO"),
    JUDICIARY("JUDICIARIO");

    private final String name;

    Branch(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Branch fromValue(String value) {
        for (Branch branch : Branch.values()) {
            if (branch.getName().equalsIgnoreCase(value)) {
                return branch;
            }
        }

        throw new IllegalArgumentException("Unexpected value: " + value);
    }

    @Override
    public String toString() {
        return name;
    }
}
