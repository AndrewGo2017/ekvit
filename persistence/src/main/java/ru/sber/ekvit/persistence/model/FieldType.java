package ru.sber.ekvit.persistence.model;

import lombok.Getter;

public enum FieldType {
    C("Строка"),
    N("Число"),
    D("Дата"),
    L("Булево");

    @Getter
    private String str;

    FieldType(String str) {
        this.str = str;
    }
}
