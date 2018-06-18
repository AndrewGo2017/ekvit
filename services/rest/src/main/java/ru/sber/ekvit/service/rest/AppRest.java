package ru.sber.ekvit.service.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"ru.sber.ekvit.persistence.dao.datajpa"})
@EntityScan(basePackages = {"ru.sber.ekvit.persistence.model"})
@ComponentScan({"ru.sber.ekvit.persistence.dao", "ru.sber.ekvit.service.rest"})
public class AppRest {
    public static void main(String[] args){
        SpringApplication.run(AppRest.class, args);
    }
}
