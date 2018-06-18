package ru.sber.ekvit.service.rest.charge;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sber.ekvit.service.rest.charge.model.ResponseCharge;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

@RestController
@Slf4j
@RequestMapping("/charge")
public class ChargeController {
    private final ChargeService chargeService;

    @Autowired
    public ChargeController(ChargeService chargeService) {
        this.chargeService = chargeService;
    }

    @RequestMapping(value = "/{street}/{house}/{apartment}", produces = APPLICATION_XML_VALUE)
    public ResponseCharge getCharge(@PathVariable("street") String street, @PathVariable("house") String house, @PathVariable("apartment") String apartment){
        log.info("getCharge");

        return chargeService.getResponseCharge(street, house, apartment);
    }
}