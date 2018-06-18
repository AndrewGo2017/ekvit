package ru.sber.ekvit.persistence.dao.datajpa.crud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.ekvit.persistence.model.Contract;

public interface ContractCrudDao extends JpaRepository<Contract, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Contract c WHERE c.id=:id")
    int delete(@Param("id") int id);
}
