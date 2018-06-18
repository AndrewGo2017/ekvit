//
// base class for handling files (registers)
// with address handling logic
//


package ru.sber.ekvit.job.register;

import lombok.AllArgsConstructor;
import ru.sber.ekvit.job.JobType;
import ru.sber.ekvit.persistence.dao.AddressElementDao;
import ru.sber.ekvit.persistence.dao.ApplicationEventDao;
import ru.sber.ekvit.persistence.dao.BaseDao;
import ru.sber.ekvit.persistence.dao.ContractDao;
import ru.sber.ekvit.persistence.model.AddressElement;
import ru.sber.ekvit.persistence.model.BaseEntity;

import java.nio.file.Path;
import java.util.Map;

import static ru.sber.ekvit.job.util.CommonUtil.tryParseInt;

abstract class BaseAdrRegister<T extends BaseEntity> extends BaseRegister<T> {
    BaseAdrRegister(BaseDao<T> baseDao, ContractDao contractDao, ApplicationEventDao applicationEventDao, Path searchDirectory, JobType jobType) {
        super(baseDao, contractDao, applicationEventDao, searchDirectory, jobType);
    }

    //get address from received string(s) (addressNameValueElementMap) with conditions specified (addressElement)
    Adr getAdr(Map<String, String> addressNameValueElementMap, AddressElement addressElement) {
        String fullElement;
        String[] subElements;

        String street;
        fullElement = addressNameValueElementMap.get(addressElement.getFieldStreet());
        try {
            subElements = fullElement.split(addressElement.getDelimiter());
            street = subElements[tryParseInt(addressElement.getPosStreet()) - 1];
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            street = "";
        }

        String house;
        fullElement = addressNameValueElementMap.get(addressElement.getFieldHouse());
        try {
            subElements = fullElement.split(addressElement.getDelimiter());

            house = subElements[tryParseInt(addressElement.getPosHouse()) - 1];
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            house = "";
        }

        String apartment;
        fullElement = addressNameValueElementMap.get(addressElement.getFieldApartment());
        try {
            subElements = fullElement.split(addressElement.getDelimiter());
            apartment = subElements[tryParseInt(addressElement.getPosApartment()) - 1];
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            apartment = "";
        }

        return new Adr(street, house, apartment);
    }

    @AllArgsConstructor
    class Adr {
        String street;
        String house;
        String apartment;
    }

    //remove common parts to make address more universal
    String normalizeAdrString(String str){
        return str
                .replace("ул.", "")
                .replace("ул ", "")
                .replace("д.","")
                .replace("д ", "")
                .replace("кв.", "")
                .replace("кв", "")
                .replace("  ", " ")
                .toLowerCase()
                .trim();
    }

    Adr normalizeAdr(Adr adr){
        return new Adr(normalizeAdrString(adr.street), normalizeAdrString(adr.house), normalizeAdrString(adr.apartment));
    }
}
