package com.examples.parserdemo.model;

/**
 * Created by konstantin on 10.04.2020.
 */
public enum ItemType {
    APPLE("apple"),
    CAR("car"),
    GENERAL("general");

    private String value;

    ItemType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
