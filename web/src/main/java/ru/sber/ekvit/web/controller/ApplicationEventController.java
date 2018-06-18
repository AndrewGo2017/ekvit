package ru.sber.ekvit.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sber.ekvit.persistence.Authorization;
import ru.sber.ekvit.persistence.dao.BaseDao;
import ru.sber.ekvit.persistence.model.ApplicationEvent;

import java.util.List;

@Controller
@RequestMapping("/applicationEvents")
@Slf4j
public class ApplicationEventController extends BaseController<ApplicationEvent> {

    @Autowired
    public ApplicationEventController(BaseDao<ApplicationEvent> dao) {
        super(dao);
    }

    @GetMapping
    public String index(Model m){
        log.info("index user {}", Authorization.id());

        m.addAttribute("noEdit", "");
        m.addAttribute("hideAddButton", "");
        m.addAttribute("title", "События");
        return "common";
    }

    @GetMapping("/all")
    public String getAll(Model m){
        log.info("getAll user {}", Authorization.id());

        List<ApplicationEvent> applicationEvents = _getAll();
        m.addAttribute("applicationEvents", applicationEvents);
        return "fragments/tables :: applicationEventsList";
    }
}
