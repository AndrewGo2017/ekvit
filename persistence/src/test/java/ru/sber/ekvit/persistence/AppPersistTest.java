package ru.sber.ekvit.persistence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories({"ru.sber.ekvit.persistence.dao.datajpa"})
@EntityScan({"ru.sber.ekvit.persistence.model"})
@ComponentScan({"ru.sber.ekvit.persistence.dao"})
public class AppPersistTest {
    public static void main(String[] args) {
        SpringApplication.run(AppPersistTest.class, args);
    }
}