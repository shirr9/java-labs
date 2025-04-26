package com.Lab3.models;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Color {
    BLACK,
    WHITE,
    GREEN,
    BROWN,
    MULTICOLOR;

    @Override
    @JsonValue
    public String toString() {
        return name();
    }
    @JsonCreator
    public static Color fromString(String value) {
        if (value == null) {
            return null;
        }
        try {
            return Color.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid color: " + value);
        }
    }
}