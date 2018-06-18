package ru.sber.ekvit.persistence.model;

import lombok.Getter;

public enum FileEncoding {
    DOS ("cp866"),
    WIN ("windows-1251"),
    UTF ("utf-8");

    @Getter
    private String str;

    FileEncoding(String str) {
        this.str = str;
    }
}
