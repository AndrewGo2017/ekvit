package ru.sber.ekvit.persistence.to;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import ru.sber.ekvit.persistence.model.FieldType;
import ru.sber.ekvit.persistence.model.RegisterIn;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RegisterInTo extends BaseTo {
    private Integer contract;

    private Integer article;

    private String fieldName;

    private FieldType fieldType;

    private Integer length;

    private Integer precision;

    private String description;

    private String code;

    private String linkedFieldName;

    public RegisterInTo(Integer id, Integer contract, Integer article, String fieldName, FieldType fieldType, Integer length, Integer precision, String description, String code, String linkedFieldName) {
        super(id);
        this.contract = contract;
        this.article = article;
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.length = length;
        this.precision = precision;
        this.description = description;
        this.code = code;
        this.linkedFieldName = linkedFieldName;
    }

    public RegisterInTo(RegisterIn registerIn){
        this(registerIn.getId(), registerIn.getContract().getId(), registerIn.getArticle().getId(), registerIn.getFieldName(), registerIn.getFieldType(), registerIn.getLength(), registerIn.getPrecision(), registerIn.getDescription(), registerIn.getCode(), registerIn.getLinkedFieldName());
    }
}
