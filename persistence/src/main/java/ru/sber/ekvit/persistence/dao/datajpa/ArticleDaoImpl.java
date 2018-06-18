package ru.sber.ekvit.persistence.dao.datajpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.sber.ekvit.persistence.Authorization;
import ru.sber.ekvit.persistence.model.Article;
import ru.sber.ekvit.persistence.dao.ArticleDao;
import ru.sber.ekvit.persistence.dao.datajpa.crud.ArticleCrudDao;

import java.util.List;

@Repository
@Slf4j
public class ArticleDaoImpl implements ArticleDao {

    private final ArticleCrudDao articleCrudDao;

    @Autowired
    public ArticleDaoImpl(ArticleCrudDao contractorCrudDao) {
        this.articleCrudDao = contractorCrudDao;
    }

    @Override
    public Article save(Article entity) {
        log.info("save {} ", entity);

        return articleCrudDao.save(entity);
    }

    @Override
    public void saveAll(List<Article> entities) {
        log.info("saveAll ");

        articleCrudDao.saveAll(entities);
    }

    @Override
    public boolean delete(int id) {
        log.info("delete id {} ", id);

        return articleCrudDao.delete(id) != 0;
    }

    @Override
    public Article get(int id) {
        log.info("get id {} ", id);

        return articleCrudDao.findById(id).orElse(null);
    }

    @Override
    public List<Article> getAll() {
        log.info("getAll ");

        return articleCrudDao.findAll();
    }

    @Override
    public Article getOne(int id) {
        log.info("getOne id {} ", id);

        return articleCrudDao.getOne(id);
    }
}
