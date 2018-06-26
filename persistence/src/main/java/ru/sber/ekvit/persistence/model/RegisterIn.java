package ru.sber.ekvit.persistence.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "register_in")
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class RegisterIn extends BaseEntity {

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "article_id")
    private Article article;

    @NonNull
    @Column(name = "field_name")
    private String fieldName;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "field_type")
    private FieldType fieldType;

    @NonNull
    @Column(name = "field_length")
    private Integer length;

    @NonNull
    @Column(name = "field_precision")
    private Integer precision;

    @Column(name = "description")
    @NonNull
    private String description;

    @Column(name = "code")
    @NonNull
    private String code;

    @Column(name = "linked_field_name")
    @NonNull
    private String linkedFieldName;

    public RegisterIn(Integer id, Contract contract, Article article, String fieldName, FieldType fieldType, Integer length, Integer precision, String description, String code, String linkedFieldName) {
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

    public RegisterIn(RegisterIn registerIn){
        this(registerIn.getId(), registerIn.getContract(), registerIn.getArticle(), registerIn.getFieldName(), registerIn.getFieldType(), registerIn.getLength(), registerIn.getPrecision(), registerIn.getDescription(), registerIn.getCode(), registerIn.getLinkedFieldName());
    }

    public boolean isLs(){
        return article.id == 1;
    }

    public boolean isAddress(){
        return article.id == 2;
    }
}