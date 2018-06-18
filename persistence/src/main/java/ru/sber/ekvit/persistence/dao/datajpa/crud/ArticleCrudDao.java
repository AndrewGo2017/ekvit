package ru.sber.ekvit.persistence.dao.datajpa.crud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.ekvit.persistence.model.Article;

public interface ArticleCrudDao extends JpaRepository<Article, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Article a WHERE a.id=:id")
    int delete(@Param("id") int id);
}
