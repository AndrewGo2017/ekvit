package ru.sber.ekvit.persistence.model;

import lombok.*;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import java.time.LocalDateTime;

@Entity
@Table(name = "charges")
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Charge extends BaseEntity {
    public static final String ARTICLE_TAG_NAME = "article"; //info field contents. name of json/xml element. same as followings
    public static final String FIELDNAME_TAG_NAME = "fieldName";
    public static final String FIELDVALUE_TAG_NAME = "fieldValue";
    public static final String ADDITIONALVALUE_TAG_NAME = "additionalValue";
    public static final String ITEMWRAPPER_TAG_NAME = "items";

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @NonNull
    @Column(name = "ls")
    private String ls;

    @NonNull
    @Column(name = "address")
    private String address;

    @NonNull
    @Column(name = "street")
    private String street;

    @NonNull
    @Column(name = "house")
    private String house;

    @NonNull
    @Column(name = "apartment")
    private String apartment;

    @NonNull
    @Column(name = "info")
    private String info;

    @NonNull
    @Column(name = "code")
    private String code;

    @NonNull
    @Column(name = "upload_date")
    private LocalDateTime uploadDate;

    public Charge(Integer id, Contract contract, String ls, String address, String street, String house, String apartment, String info, String code, LocalDateTime uploadDate) {
        super(id);
        this.contract = contract;
        this.ls = ls;
        this.address = address;
        this.street = street;
        this.house = house;
        this.apartment = apartment;
        this.info = info;
        this.code = code;
        this.uploadDate = uploadDate;
    }

    public Charge(Charge charge) {
        this(charge.getId(), charge.getContract(), charge.getLs(), charge.getAddress(), charge.getStreet(), charge.getHouse(), charge.getApartment(), charge.getInfo(), charge.getCode() ,charge.getUploadDate());
    }
}