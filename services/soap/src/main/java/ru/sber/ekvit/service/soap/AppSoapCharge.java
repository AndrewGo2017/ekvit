package ru.sber.ekvit.service.soap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories({"ru.sber.ekvit.persistence.dao.datajpa"})
@EntityScan({"ru.sber.ekvit.persistence.model"})
@ComponentScan({"ru.sber.ekvit.persistence.dao", "ru.sber.ekvit.service.soap.charge", "ru.sber.ekvit.service.rest.charge"})
@SpringBootApplication
public class AppSoapCharge {
    public static void main(String[] args){
        SpringApplication.run(AppSoapCharge.class, args);
    }
}

