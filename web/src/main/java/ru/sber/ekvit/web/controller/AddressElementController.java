package ru.sber.ekvit.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sber.ekvit.persistence.Authorization;
import ru.sber.ekvit.persistence.dao.AddressElementDao;
import ru.sber.ekvit.persistence.dao.ContractDao;
import ru.sber.ekvit.persistence.model.AddressElement;
import ru.sber.ekvit.persistence.model.Contract;
import ru.sber.ekvit.persistence.to.AddressElementTo;

import java.util.List;

@Controller
@RequestMapping("/addressElement")
@Slf4j
public class AddressElementController extends BaseToController<AddressElement, AddressElementTo> {
    private final ContractDao contractDao;

    @Autowired
    public AddressElementController(AddressElementDao addressElementDao, ContractDao contractDao) {
        super(addressElementDao);
        this.contractDao = contractDao;
    }

    @GetMapping
    public String index(Model m){
        log.info("index user {}", Authorization.id());

        m.addAttribute("title", "Элементы адреса");
        return "common";
    }

    @GetMapping("/{id}")
    public String get(@PathVariable("id") Integer id, Model m){
        log.info("get id {} user {}",id, Authorization.id());

        AddressElement addressElement = _get(id);
        List<Contract> contracts = contractDao.getAll();
        m.addAttribute("contracts", contracts);
        m.addAttribute("addressElement",addressElement);
        return "fragments/dialogs :: addressElementDialog";
    }

    @PostMapping
    public String save(AddressElementTo entity) {
        log.info("save {} user {}",entity, Authorization.id());

        _save(entity);
        return "common";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id) {
        log.info("delete id {} user {}",id, Authorization.id());

        _delete(id);
        return "common";
    }

    @GetMapping("/all")
    public String getAll(Model m){
        log.info("getAll user {}", Authorization.id());

        List<AddressElement> addressElements = _getAll();
        m.addAttribute("addressElements", addressElements);
        return "fragments/tables :: addressElementList";
    }
}