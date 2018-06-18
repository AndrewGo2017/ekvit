package ru.sber.ekvit.persistence.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "correct_addresses")
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CorrectAddress extends BaseEntity {
    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private Address address;

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @NonNull
    @Column( name = "ls")
    private String ls;

    public CorrectAddress(Integer id, Address address, Contract contract, String ls) {
        super(id);
        this.address = address;
        this.contract = contract;
        this.ls = ls;
    }

    public CorrectAddress(CorrectAddress correctAddress){
        this(correctAddress.getId(), correctAddress.getAddress(), correctAddress.getContract(), correctAddress.getLs());
    }
}