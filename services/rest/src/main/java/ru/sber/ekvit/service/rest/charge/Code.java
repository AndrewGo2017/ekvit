package ru.sber.ekvit.service.rest.charge;

public enum Code {
    Ok(0),
    Error(0);

    private Integer str;

    Code(Integer str){
        this.str = str;
    }

    public Integer str(){
        return str;
    }
}
