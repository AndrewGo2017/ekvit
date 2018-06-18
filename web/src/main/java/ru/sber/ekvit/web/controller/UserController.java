package ru.sber.ekvit.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sber.ekvit.persistence.Authorization;
import ru.sber.ekvit.persistence.dao.BaseDao;
import ru.sber.ekvit.persistence.model.User;

import java.util.List;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController extends BaseController<User> {

    @Autowired
    public UserController(BaseDao<User> dao) {
        super(dao);
    }

    @GetMapping
    public String index(Model m){
        log.info("index user {}", Authorization.id());

        m.addAttribute("title", "Пользователи");
        return "common";
    }

    @GetMapping("/{id}")
    public String get(@PathVariable("id") Integer id, Model m){
        log.info("get id {} user {}",id, Authorization.id());

        User user = _get(id);
        m.addAttribute("user",user);
        return "fragments/dialogs :: userDialog";
    }

    @PostMapping
    public String save(User entity) {
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

        List<User> users = _getAll();
        m.addAttribute("users", users);
        return "fragments/tables :: usersList";
    }
}