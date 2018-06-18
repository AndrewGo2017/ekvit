package ru.sber.ekvit.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sber.ekvit.persistence.Authorization;
import ru.sber.ekvit.persistence.dao.BaseDao;
import ru.sber.ekvit.persistence.model.Article;
import ru.sber.ekvit.persistence.model.User;

import java.util.List;

@Controller
@RequestMapping("/login")
@Slf4j
public class LogInController extends BaseController<User> {

    @Autowired
    public LogInController(BaseDao<User> dao) {
        super(dao);
    }

    @GetMapping
    public String index(Model m){
        log.info("index");

        m.addAttribute("users", _getAll());
        m.addAttribute("title", "Авторизация");
        return "login";
    }
}
