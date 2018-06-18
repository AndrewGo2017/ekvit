package ru.sber.ekvit.persistence.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "articles")
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Article extends BaseEntity {
    @NonNull
    @Column(name = "name")
    private String name;

    public Article(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public Article(Article article) {
        this(article.getId(), article.getName());
    }
}