package ru.sber.ekvit.persistence.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "contractors")
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Contractor extends BaseEntity {

    @NonNull
    @Column(name = "name")
    private String name;

    @NonNull
    @Column(name = "full_name")
    private String fullName;

    @NonNull
    @Column(name = "inn")
    private String inn;

    @NonNull
    @Column(name = "pay_account")
    private String payAccount;

    @NonNull
    @Column(name = "bic")
    private String bic;

    public Contractor(Integer id, String name, String fullName, String inn, String payAccount, String bic) {
        super(id);
        this.name = name;
        this.fullName = fullName;
        this.inn = inn;
        this.payAccount = payAccount;
        this.bic = bic;
    }

    public Contractor(Contractor contractor){
        this(contractor.getId(), contractor.getName(), contractor.getFullName(), contractor.getInn(), contractor.getPayAccount(), contractor.getBic());
    }
}