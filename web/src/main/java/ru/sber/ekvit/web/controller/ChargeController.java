package ru.sber.ekvit.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sber.ekvit.persistence.Authorization;
import ru.sber.ekvit.persistence.dao.ChargeDao;
import ru.sber.ekvit.persistence.dao.ContractDao;
import ru.sber.ekvit.persistence.model.Charge;
import ru.sber.ekvit.persistence.model.Contract;
import ru.sber.ekvit.persistence.to.ChargeTo;

import java.util.List;


@Controller
@RequestMapping("/charge")
@Slf4j
public class ChargeController {
    private final ChargeDao chargeDao;

    private final ContractDao contractDao;

    @Autowired
    public ChargeController(ChargeDao chargeDao, ContractDao contractDao) {
        this.chargeDao = chargeDao;
        this.contractDao = contractDao;
    }

    @GetMapping
    public String index(Model m){
        log.info("index user {}", Authorization.id());

        m.addAttribute("title", "Начисления");
        return "charge";
    }

    @GetMapping("/all")
    public String getAll(Model m,
                         @RequestParam(required = false) Integer contract,
                         @RequestParam(required = false) String street,
                         @RequestParam(required = false) String house,
                         @RequestParam(required = false) String apartment){
        log.info("getAll ( with contract: {}, street: {}, house: {}, apartment: {} ) user {}", contract, street, house, apartment, Authorization.id());


        List<Charge> charges;
        if (contract == null || street == null || house == null || apartment == null){
            charges = chargeDao.getFirst100();
        } else {
            charges = chargeDao.getFirst100ByContractIdAndStreetAndHouseAndApartment(contract, street, house, apartment);
        }

        m.addAttribute("charges", charges);
        return "fragments/tables :: chargeList";
    }

    @GetMapping("/{id}")
    public String get(@PathVariable("id") Integer id, Model m) {
        log.info("get id {} user {}",id, Authorization.id());

        Charge charge = chargeDao.get(id);
        List<Contract> contracts = contractDao.getAll();
        m.addAttribute("contracts", contracts);
        m.addAttribute("charge", charge);
        return "fragments/dialogs :: chargeDialog";
    }

    @PostMapping
    public String save(ChargeTo chargeTo, @RequestParam(required = false) Boolean allStreets, @RequestParam(required = false) Boolean allHouses, @RequestParam(required = false) Boolean isNew) {
        log.info("save TO {} ( with allStreets: {}, allHouses: {}, isNew: {}) user {}",chargeTo, allStreets, allHouses, isNew, Authorization.id());

        if (isNew != null && isNew) {
            chargeDao.addNewAddress(chargeTo);
        } else {
            if (allStreets != null && allHouses != null) {
                if (allStreets && allHouses) {
                    chargeDao.updateAllHouses(chargeTo);
                } else if (allStreets) {
                    chargeDao.updateAllStreets(chargeTo);
                }
            } else {
                chargeDao.update(chargeTo);
            }
        }

        return "charge";
    }

    @GetMapping("/streets")
    public @ResponseBody List<String> getStreets(@RequestParam("street") String street){
        log.info("getStreets ( with street: {} ) user {}", street, Authorization.id());

        return chargeDao.getStreets(street);
    }

    @GetMapping("/houses")
    public @ResponseBody List<String> getHouses(@RequestParam("street") String street, @RequestParam("house") String house){
        log.info("getHouses ( with street: {}, house: {} ) user {}", street, house, Authorization.id());

        return chargeDao.getHouses(street, house);
    }

    @GetMapping("/apartments")
    public @ResponseBody List<String> getApartments(@RequestParam("street") String street, @RequestParam("house") String house, @RequestParam("apartment") String apartment){
        log.info("getApartments ( with street: {}, house: {}, apartment: {} ) user {}", street, house, apartment, Authorization.id());

        return chargeDao.getApartment(street, house, apartment);
    }

    @GetMapping("/status")
    public @ResponseBody Integer getApartments(@RequestParam("chargeId") Integer chargeId){
        log.info("getApartments ( with chargeId: {}) user {}",chargeId, Authorization.id());

        return chargeDao.getAddressNotFoundStatus(chargeId);
    }
}
