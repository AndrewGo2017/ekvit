package ru.sber.ekvit.persistence.dao.datajpa.crud;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.ekvit.persistence.model.CorrectAddress;
import ru.sber.ekvit.persistence.model.User;

import java.util.List;


public interface CorrectAddressCrudDao extends JpaRepository<CorrectAddress, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM CorrectAddress c WHERE c.id=:id")
    int delete(@Param("id") int id);

    List<CorrectAddress> findFirstByOrderByIdAsc();
}
