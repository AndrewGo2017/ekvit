package ru.sber.ekvit.service.soap.charge;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.sber.ekvit.persistence.dao.ContractDao;
import ru.sber.ekvit.service.rest.charge.ChargeService;
import ru.sber.ekvit.service.rest.charge.model.ResponseCharge;
import ru.sber.ekvit.service.soap.charge.wsdl.Address;
import ru.sber.ekvit.service.soap.charge.wsdl.GetChargesByAddressRequest;
import ru.sber.ekvit.service.soap.charge.wsdl.GetChargesByAddressResponse;

@org.springframework.ws.server.endpoint.annotation.Endpoint
@Slf4j
public class Endpoint {
    private static final String NAMESPACE_URI = "http://ekvit.sber.ru";

    private final ChargeService chargeService;

    @Autowired
    public Endpoint(ChargeService chargeService) {
        this.chargeService = chargeService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getChargesByAddressRequest")
    @ResponsePayload
    public GetChargesByAddressResponse getChargesByAddress(@RequestPayload GetChargesByAddressRequest request){
        log.info("GetChargesByAddressResponse ( with request: {} )", request);

        Address address = request.getAddress();
        ResponseCharge responseCharge = chargeService.getResponseCharge(address.getStreet(), address.getHouse(), address.getApartment());

        GetChargesByAddressResponse response = new GetChargesByAddressResponse();
        response.setCharges(responseCharge.toString());//soap made just for test, so return string
        return response;
    }
}
