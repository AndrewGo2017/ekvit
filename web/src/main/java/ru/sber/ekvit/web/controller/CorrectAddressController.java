package ru.sber.ekvit.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sber.ekvit.persistence.Authorization;
import ru.sber.ekvit.persistence.dao.CorrectAddressDao;
import ru.sber.ekvit.persistence.model.CorrectAddress;
import ru.sber.ekvit.persistence.to.CorrectAddressTo;

import java.util.List;

@Controller
@RequestMapping("/correctAddress")
@Slf4j
public class CorrectAddressController extends BaseToController<CorrectAddress, CorrectAddressTo> {

    @Autowired
    public CorrectAddressController(CorrectAddressDao correctAddressDao) {
        super(correctAddressDao);
    }

    @GetMapping
    public String index(Model m){
        log.info("index user {}", Authorization.id());

        m.addAttribute("title", "Сопоставленные адреса");
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

        List<CorrectAddress> correctAddresses = _getAll();
        m.addAttribute("correctAddresses", correctAddresses);
        return "fragments/tables :: correctAddressList";
    }
}
