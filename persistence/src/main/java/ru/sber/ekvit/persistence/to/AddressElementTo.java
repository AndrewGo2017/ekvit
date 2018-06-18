package ru.sber.ekvit.persistence.to;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.sber.ekvit.persistence.model.AddressElement;
import ru.sber.ekvit.persistence.model.Contractor;
import ru.sber.ekvit.persistence.model.FileEncoding;
import ru.sber.ekvit.persistence.model.FileType;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AddressElementTo extends BaseTo {
    private Integer contract;

    private String delimiter;

    private String fieldStreet;

    private String posStreet;

    private String fieldHouse;

    private String posHouse;

    private String fieldApartment;

    private String posApartment;

    public AddressElementTo(Integer id, Integer contract, String delimiter, String fieldStreet, String posStreet, String fieldHouse, String posHouse, String fieldApartment, String posApartment) {
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

    public AddressElementTo( AddressElementTo addressElement) {
        this(addressElement.getId(), addressElement.getContract(), addressElement.getDelimiter(), addressElement.getFieldStreet(), addressElement.getPosStreet(), addressElement.getFieldHouse(), addressElement.getPosHouse(), addressElement.getFieldApartment(), addressElement.getPosApartment());
    }

    public AddressElementTo( AddressElement addressElement) {
        this(addressElement.getId(), addressElement.getContract().getId(), addressElement.getDelimiter(), addressElement.getFieldStreet(), addressElement.getPosStreet(), addressElement.getFieldHouse(), addressElement.getPosHouse(), addressElement.getFieldApartment(), addressElement.getPosApartment());
    }
}
