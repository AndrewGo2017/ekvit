package ru.sber.ekvit.persistence.to;

import lombok.*;
import ru.sber.ekvit.persistence.model.BaseEntity;
import ru.sber.ekvit.persistence.model.Contract;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ChargeTo extends BaseTo {

    private Integer contract;

    private String street;

    private String house;

    private String apartment;

    public ChargeTo(Integer id, Integer contract, String street, String house, String apartment) {
        super(id);
        this.contract = contract;

        this.street = street;
        this.house = house;
        this.apartment = apartment;
    }
}