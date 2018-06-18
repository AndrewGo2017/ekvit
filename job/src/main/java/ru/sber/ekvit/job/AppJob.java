package ru.sber.ekvit.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories(basePackages = {"ru.sber.ekvit.persistence.dao.datajpa"})
@EntityScan(basePackages = {"ru.sber.ekvit.persistence.model"})
@ComponentScan(basePackages = {"ru.sber.ekvit.job", "ru.sber.ekvit.persistence.dao"})
public class AppJob {
    public static void main(String[] args) {
        SpringApplication.run(AppJob.class, args);
    }
}