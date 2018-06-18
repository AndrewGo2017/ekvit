package ru.sber.ekvit.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.sber.ekvit.service.soap.charge.ChargeClient;
import ru.sber.ekvit.service.soap.charge.wsdl.GetChargesByAddressResponse;
import ru.sber.ekvit.service.soap.AppSoapCharge;

import java.util.Arrays;

@SpringBootApplication
public class AppSoapChargeTest {
    public static void main(String[] args) {
        Class[] classes = (Class[]) Arrays.asList(AppSoapCharge.class, AppSoapChargeTest.class).toArray();
        SpringApplication.run(classes, args);
    }

    @Bean
    CommandLineRunner lookup(ChargeClient chargeClient) {
        return args -> {
            GetChargesByAddressResponse response = chargeClient.getQuote();
        };
    }
}

