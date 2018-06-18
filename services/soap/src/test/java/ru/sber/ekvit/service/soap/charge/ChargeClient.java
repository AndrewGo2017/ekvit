package ru.sber.ekvit.service.soap.charge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import ru.sber.ekvit.service.soap.charge.wsdl.Address;
import ru.sber.ekvit.service.soap.charge.wsdl.GetChargesByAddressRequest;
import ru.sber.ekvit.service.soap.charge.wsdl.GetChargesByAddressResponse;

public class ChargeClient extends WebServiceGatewaySupport  {
    private static final Logger log = LoggerFactory.getLogger(ChargeClient.class);

    public GetChargesByAddressResponse getQuote(

    ) {

        GetChargesByAddressRequest request = new GetChargesByAddressRequest();
        Address address = new Address();
        address.setCity("omsk");
        address.setStreet("ленина");
        address.setHouse("33");
        address.setApartment("2");
        request.setAddress(address);

        GetChargesByAddressResponse response = (GetChargesByAddressResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8098/ekvit/ws",
                        request);

        return response;
    }
}
