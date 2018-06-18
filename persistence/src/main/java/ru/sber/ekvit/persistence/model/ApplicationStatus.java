package ru.sber.ekvit.persistence.model;

import lombok.Getter;

public enum ApplicationStatus {
    INFO ("Успешно"),
    ERROR("Ошибка");

    @Getter
    private String str;

    ApplicationStatus(String str){
        this.str = str;
    }
}
