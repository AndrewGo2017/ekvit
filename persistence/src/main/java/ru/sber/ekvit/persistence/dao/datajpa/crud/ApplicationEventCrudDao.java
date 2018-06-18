package ru.sber.ekvit.persistence.dao.datajpa.crud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.ekvit.persistence.model.ApplicationEvent;

import java.util.List;

public interface ApplicationEventCrudDao extends JpaRepository<ApplicationEvent, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM ApplicationEvent a WHERE a.id=:id")
    int delete(@Param("id") int id);

    List<ApplicationEvent> findFirst100ByOrderByIdDesc();
}
