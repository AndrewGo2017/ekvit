package ru.sber.ekvit.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sber.ekvit.persistence.Authorization;
import ru.sber.ekvit.persistence.dao.BaseDao;
import ru.sber.ekvit.persistence.model.Contractor;
import ru.sber.ekvit.persistence.dao.ContractorDao;

import java.util.List;

@Controller
@RequestMapping("/contractor")
@Slf4j
public class ContractorController extends BaseController<Contractor> {

    @Autowired
    public ContractorController(BaseDao<Contractor> dao) {
        super(dao);
    }

    @GetMapping
    public String index(Model m){
        log.info("index user {}", Authorization.id());

        m.addAttribute("title", "Контрагенты");
        return "common";
    }

    @GetMapping("/{id}")
    public String get(@PathVariable("id") Integer id, Model m){
        log.info("get id {} user {}",id, Authorization.id());

        Contractor contractor = _get(id);
        m.addAttribute("contractor",contractor);
        return "fragments/dialogs :: contractorDialog";
    }

    @PostMapping
    public String save(Contractor entity) {
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

        List<Contractor> contractors = _getAll();
        m.addAttribute("contractors", contractors);
        return "fragments/tables :: contractorsList";
    }
}
