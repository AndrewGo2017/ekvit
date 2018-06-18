package ru.sber.ekvit.persistence.dao;


import ru.sber.ekvit.persistence.model.Article;

public interface ArticleDao extends BaseDao<Article> {
    Article getOne(int id);
}
