package ru.sber.ekvit.persistence.dao.datajpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.sber.ekvit.persistence.Authorization;
import ru.sber.ekvit.persistence.dao.datajpa.crud.ContractCrudDao;
import ru.sber.ekvit.persistence.model.Charge;
import ru.sber.ekvit.persistence.dao.ChargeDao;
import ru.sber.ekvit.persistence.dao.datajpa.crud.ChargeCrudDao;
import ru.sber.ekvit.persistence.to.ChargeTo;

import java.util.List;

@Repository
@Slf4j
public class ChargeDaoImpl implements ChargeDao {

    private final ChargeCrudDao chargeCrudDao;

    private final ContractCrudDao contractCrudDao;

    @Autowired
    public ChargeDaoImpl(ChargeCrudDao chargeCrudDao, ContractCrudDao contractCrudDao) {
        this.chargeCrudDao = chargeCrudDao;
        this.contractCrudDao = contractCrudDao;
    }

    @Override
    public Charge save(Charge entity) {
        log.info("save {} ", entity);

        entity.setStreet(entity.getStreet().toLowerCase());
        entity.setHouse(entity.getHouse().toLowerCase());
        entity.setApartment(entity.getApartment().toLowerCase());
        return chargeCrudDao.save(entity);
    }

    @Override
    public boolean delete(int id) {
        log.info("delete id {} ", id);

        return chargeCrudDao.delete(id) != 0;
    }

    @Override
    public Charge get(int id) {
        log.info("get id {} ", id);

        return chargeCrudDao.findById(id).orElse(null);
    }

    @Override
    public List<Charge> getAll() {
        log.info("getAll ");

        return getFirst100ByContractId(1);
    }

    @Override
    public List<Charge> getFirst100ByContractIdAndStreetAndHouseAndApartment(int contractId, String street, String house, String apartment) {
        log.info("getFirst100ByContractIdAndStreetAndHouseAndApartment ( with contractId: {}, street: {}, house: {}, apartment: {} ) ", contractId, street, house, apartment);

        return chargeCrudDao.getAllByContractAndAddress(
                contractId,
                street, house, apartment);
    }

    @Override
    public List<Charge> getFirst100() {
        log.info("getFirst100 ");

        return chargeCrudDao.findFirst100ByOrderByIdAsc();
    }

    @Override
    public List<Charge> getFirst100ByContractId(int contractId) {
        log.info("getFirst100ByContractId {} ", contractId);

        return chargeCrudDao.getTop100ByContractId(contractId);
    }

    @Override
    public void update(ChargeTo chargeTo) {
        log.info("update TO {} ", chargeTo);

        chargeTo.setStreet(chargeTo.getStreet().toLowerCase());
        chargeTo.setHouse(chargeTo.getHouse().toLowerCase());
        chargeTo.setApartment(chargeTo.getApartment().toLowerCase());
        chargeCrudDao.updateCharge(chargeTo.getId(), chargeTo.getStreet(), chargeTo.getHouse(), chargeTo.getApartment());
    }

    @Override
    public void updateAllStreets(ChargeTo chargeTo) {
        log.info("updateAllStreets ( with TO {}) ", chargeTo);

        chargeTo.setStreet(chargeTo.getStreet().toLowerCase());
        Charge currentCharge = chargeCrudDao.findById(chargeTo.getId()).orElse(null);
        if (currentCharge != null) {
            String currentStreet = currentCharge.getStreet();
            chargeCrudDao.updateAllStreets(chargeTo.getId(), chargeTo.getContract(), currentStreet, chargeTo.getStreet());
        }

    }

    @Override
    public void updateAllHouses(ChargeTo chargeTo) {
        log.info("updateAllHouses ( with TO {} ) ", chargeTo);

        chargeTo.setStreet(chargeTo.getStreet().toLowerCase());
        chargeTo.setHouse(chargeTo.getHouse().toLowerCase());
        Charge currentCharge = chargeCrudDao.findById(chargeTo.getId()).orElse(null);
        if (currentCharge != null) {
            String currentHouse = currentCharge.getHouse();
            chargeCrudDao.updateAllHouses(chargeTo.getId(), chargeTo.getContract(), chargeTo.getStreet(), currentHouse, chargeTo.getHouse());
        }
    }

    @Override
    public List<String> getStreets(String street) {
        log.info("getStreets ( with street: {} ) ", street);

        return chargeCrudDao.getStreets(street);
    }

    @Override
    public List<String> getHouses(String street, String house) {
        log.info("getHouses ( with street: {}, house: {} ) ", street, house);

        return chargeCrudDao.getHouses(street, house);
    }

    @Override
    public List<String> getApartment(String street, String house, String apartment) {
        log.info("getApartment ( with street: {}, house: {}, apartment: {} ) ", street, house, apartment);

        return chargeCrudDao.getApartments(street, house, apartment);
    }

    @Override
    public int getAddressNotFoundStatus(int chargeId) {
        log.info("getAddressNotFoundStatus ( with chargeId: {} ) ", chargeId);

        return chargeCrudDao.getAddressNotFoundStatus(chargeId).get(0);
    }

    @Override
    public void addNewAddress(ChargeTo chargeTo) {
        log.info("addNewAddress ( with chargeTo: {} ) ", chargeTo);

        chargeTo.setStreet(chargeTo.getStreet().toLowerCase());
        chargeTo.setHouse(chargeTo.getHouse().toLowerCase());
        chargeTo.setApartment(chargeTo.getApartment().toLowerCase());
        //no city for now
        chargeCrudDao.addNewAddress(chargeTo.getId(),
                "", chargeTo.getStreet(), chargeTo.getHouse(), chargeTo.getApartment());
    }

    @Override
    public List<Charge> getByAddress(String street, String house, String apartment) {
        log.info("getByAddress ( with street: {}, house: {}, apartment: {} ) ", street, house, apartment);

        return chargeCrudDao.getAllByAddress(street, house, apartment);
    }

    @Override
    public void saveAll(List<Charge> charges) {
        log.info("saveAll ");

        chargeCrudDao.saveAll(charges);
    }
}