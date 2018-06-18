package ru.sber.ekvit.persistence.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "address_elements")
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class AddressElement extends BaseEntity{
    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @NonNull
    @Column(name = "delimiter")
    private String delimiter;

    @NonNull
    @Column(name = "field_street")
    private String fieldStreet;

    @NonNull
    @Column(name = "pos_street")
    private String posStreet;

    @NonNull
    @Column(name = "field_house")
    private String fieldHouse;

    @NonNull
    @Column(name = "pos_house")
    private String posHouse;

    @NonNull
    @Column(name = "field_apartment")
    private String fieldApartment;

    @NonNull
    @Column(name = "pos_apartment")
    private String posApartment;

    public AddressElement(Integer id, Contract contract, String delimiter, String fieldStreet, String posStreet, String fieldHouse, String posHouse, String fieldApartment, String posApartment) {
        super(id);
        this.contract = contract;
        this.delimiter = delimiter;
        this.fieldStreet = fieldStreet;
        this.posStreet = posStreet;
        this.fieldHouse = fieldHouse;
        this.posHouse = posHouse;
        this.fieldApartment = fieldApartment;
        this.posApartment = posApartment;
    }

    public AddressElement( AddressElement addressElement) {
        this(addressElement.getId(), addressElement.getContract(), addressElement.getDelimiter(), addressElement.getFieldStreet(), addressElement.getPosStreet(), addressElement.getFieldHouse(), addressElement.getPosHouse(), addressElement.getFieldApartment(), addressElement.getPosApartment());
    }
}
