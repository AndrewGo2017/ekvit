package ru.sber.ekvit.persistence.to;

import lombok.*;
import ru.sber.ekvit.persistence.model.Address;
import ru.sber.ekvit.persistence.model.BaseEntity;
import ru.sber.ekvit.persistence.model.Contract;
import ru.sber.ekvit.persistence.model.CorrectAddress;

import javax.persistence.*;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CorrectAddressTo extends BaseTo {
    private Integer address;

    private Integer contract;

    private String ls;

    public CorrectAddressTo(Integer id, Integer address, Integer contract, String ls) {
        super(id);
        this.address = address;
        this.contract = contract;
        this.ls = ls;
    }

    public CorrectAddressTo(CorrectAddressTo correctAddress){
        this(correctAddress.getId(), correctAddress.getAddress(), correctAddress.getContract(), correctAddress.getLs());
    }

    public CorrectAddressTo(CorrectAddress correctAddress){
        this(correctAddress.getId(), correctAddress.getAddress().getId(), correctAddress.getContract().getId(), correctAddress.getLs());
    }
}