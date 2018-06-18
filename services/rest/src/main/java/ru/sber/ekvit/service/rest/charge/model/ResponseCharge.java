package ru.sber.ekvit.service.rest.charge.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;
import ru.sber.ekvit.persistence.model.Charge;
import ru.sber.ekvit.service.rest.charge.Code;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "response")
public class ResponseCharge {
    @JacksonXmlProperty(localName = "date")
    private LocalDateTime uploadDate;

    @JacksonXmlProperty(localName = "code")
    private Integer code;

    @JacksonXmlProperty(localName = "message")
    private String message;

    @JacksonXmlElementWrapper(useWrapping=false)
    @JacksonXmlProperty(localName = "contract")
    private List<ContractCharge> contractCharges;
}