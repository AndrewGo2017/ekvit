package ru.sber.ekvit.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sber.ekvit.persistence.Authorization;
import ru.sber.ekvit.persistence.model.Contract;
import ru.sber.ekvit.persistence.model.Contractor;
import ru.sber.ekvit.persistence.dao.ContractDao;
import ru.sber.ekvit.persistence.dao.ContractorDao;
import ru.sber.ekvit.persistence.to.ContractTo;

import java.util.List;

@Controller
@RequestMapping("/contract")
@Slf4j
public class ContractController extends BaseToController<Contract, ContractTo> {
    private final ContractorDao contractorDao;
    private final ContractDao contractDao;

    @Autowired
    public ContractController(ContractorDao contractorDao, ContractDao contractDao) {
        super(contractDao);
        this.contractorDao = contractorDao;
        this.contractDao = contractDao;
    }

    @GetMapping
    public String index(Model m){
        log.info("index user {}", Authorization.id());

        m.addAttribute("title", "Договоры");
        return "common";
    }

    @GetMapping("/{id}")
    public String get(@PathVariable("id") Integer id, Model m){
        log.info("get id {} user {}",id, Authorization.id());

        Contract contract = _get(id);
        List<Contractor> contractors = contractorDao.getAll();
        m.addAttribute("fileEncodings", contractDao.getFileEncodings());
        m.addAttribute("fileTypes", contractDao.getFileTypes());
        m.addAttribute("contract",contract);
        m.addAttribute("contractors", contractors);
        return "fragments/dialogs :: contractDialog";
    }

    @PostMapping
    public String save(ContractTo entity) {
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

        List<Contract> contractors = _getAll();
        m.addAttribute("contracts", contractors);
        return "fragments/tables :: contractsList";
    }
}
