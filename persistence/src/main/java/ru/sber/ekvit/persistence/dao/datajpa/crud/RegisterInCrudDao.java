package ru.sber.ekvit.persistence.dao.datajpa.crud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.ekvit.persistence.model.RegisterIn;

import java.util.List;

public interface RegisterInCrudDao extends JpaRepository<RegisterIn, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM RegisterIn r WHERE r.id=:id")
    int delete(@Param("id") int id);

    @Query("SELECT r FROM RegisterIn r WHERE r.contract.id=:contractId")
    List<RegisterIn> getAllByContractId(@Param("contractId") int contractId);

}
