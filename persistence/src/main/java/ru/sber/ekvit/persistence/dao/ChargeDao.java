package ru.sber.ekvit.persistence.dao;


import ru.sber.ekvit.persistence.model.Charge;
import ru.sber.ekvit.persistence.to.ChargeTo;

import java.util.List;

public interface ChargeDao extends BaseDao<Charge>{
    List<Charge> getFirst100();

    List<Charge> getFirst100ByContractId(int contractId);

    List<Charge> getFirst100ByContractIdAndStreetAndHouseAndApartment(int contractId, String street, String house, String apartment);

    void update(ChargeTo chargeTo);

    void updateAllStreets(ChargeTo chargeTo);

    void updateAllHouses(ChargeTo chargeTo);

    List<String> getStreets (String street);

    List<String> getHouses (String street, String house);

    List<String> getApartment(String street, String house, String apartment);

    int getAddressNotFoundStatus(int chargeId);

    void addNewAddress(ChargeTo chargeTo);

    List<Charge> getByAddress(String street, String house, String apartment);
}
