package ru.sber.ekvit.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sber.ekvit.persistence.Authorization;
import ru.sber.ekvit.persistence.dao.ConstantDao;
import ru.sber.ekvit.persistence.model.Constant;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/constant")
@Slf4j
public class ConstantController {
    private final ConstantDao dao;

    @Autowired
    public ConstantController(ConstantDao dao) {
        this.dao = dao;
    }

    @GetMapping
    public String index(Model m) {
        log.info("index user {}", Authorization.id());

        m.addAttribute("hideAddButton", "");
        m.addAttribute("title", "Константы");
        return "common";
    }

    @GetMapping("/{id}")
    public String get(@PathVariable("id") Integer id, Model m) {
        log.info("get id {} user {}",id, Authorization.id());

        Constant constant = dao.get();
        m.addAttribute("constant", constant);
        return "fragments/dialogs :: constantDialog";
    }

    @PostMapping
    public String save(Constant entity) {
        log.info("save {} user {}",entity, Authorization.id());

        dao.save(entity);
        return "common";
    }

    @GetMapping("/all")
    public String getAll(Model m) {
        log.info("getAll user {}", Authorization.id());

        List<Constant> constants = Collections.singletonList(dao.get());
        m.addAttribute("constants", constants);
        return "fragments/tables :: constantsList";
    }
}
