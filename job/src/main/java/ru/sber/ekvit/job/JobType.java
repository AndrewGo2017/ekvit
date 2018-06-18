package ru.sber.ekvit.job;

import lombok.Getter;

public enum JobType {
    FileStructureRegisterInJob("Заполнение структуры реестра задолженности"),
    ChargesRegisterInJob("Загрузка задолженности"),
    AddressRegisterInJob("Загрузка адресов");

    @Getter
    private String str;

    JobType(String str){
        this.str = str;
    }
}