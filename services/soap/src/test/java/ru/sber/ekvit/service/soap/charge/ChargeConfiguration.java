package ru.sber.ekvit.service.soap.charge;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class ChargeConfiguration {
    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("ru.sber.ekvit.service.soap.charge.wsdl");
        return marshaller;
    }

    @Bean
    public ChargeClient chargeClient(Jaxb2Marshaller marshaller) {
        ChargeClient client = new ChargeClient();
        client.setDefaultUri("http://localhost:8098/ekvit/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
