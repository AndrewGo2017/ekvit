package ru.sber.ekvit.persistence.datajpa;

import org.junit.Test;
import ru.sber.ekvit.persistence.TestData;
import ru.sber.ekvit.persistence.model.Article;


public class ArticleDaoImpTest extends BaseDaoTest<Article> {
    @Test
    public void createTest() throws Exception {
        create(TestData.ARTICLE_NEW, TestData.ARTICLE1, TestData.ARTICLE2,  TestData.ARTICLE3,  TestData.ARTICLE_NEW );
    }

    @Test
    public void updateTest() throws Exception {
        Article updated = new Article(TestData.ARTICLE1);
        updated.setName("new name");
        update(updated);
    }

    @Test
    public void removeTest() throws Exception {
        remove(TestData.ARTICLE1.getId(), TestData.ARTICLE2, TestData.ARTICLE3);
    }

    @Test
    public void findTest() throws Exception {
       find(TestData.ARTICLE1.getId(), TestData.ARTICLE1);
    }

    @Test
    public void findAllTest() throws Exception {
        findAll(TestData.ARTICLES);
    }
}