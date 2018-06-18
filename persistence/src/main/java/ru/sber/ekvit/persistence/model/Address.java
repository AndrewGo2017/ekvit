package ru.sber.ekvit.persistence.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "addresses")
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Address extends BaseEntity {

    @NonNull
    @Column(name = "address")
    private String address;

    @NonNull
    @Column(name = "city")
    private String city;

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
    @Column(name = "code")
    private String code;

    @NonNull
    @Column(name = "upload_date")
    private LocalDateTime uploadDate;

    public Address(Integer id, String address, String city, String street, String house, String apartment, String code, LocalDateTime uploadDate) {
        super(id);
        this.address = address;
        this.city = city;
        this.street = street;
        this.house = house;
        this.apartment = apartment;
        this.code = code;
        this.uploadDate = uploadDate;
    }

    public Address(Address address) {
        this(address.getId(), address.getAddress(), address.getStreet(), address.getCity(), address.getHouse(), address.getApartment(), address.getCode() ,address.getUploadDate());
    }
}