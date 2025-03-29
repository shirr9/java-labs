package org.lab2.entity;

public enum Color {
    BLACK,
    WHITE,
    GREEN,
    BROWN,
    MULTICOLOR;

    @Override
    public String toString() {
        return this.name();
    }
}