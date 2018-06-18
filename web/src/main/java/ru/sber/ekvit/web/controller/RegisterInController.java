package ru.sber.ekvit.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sber.ekvit.persistence.Authorization;
import ru.sber.ekvit.persistence.model.Article;
import ru.sber.ekvit.persistence.model.Contract;
import ru.sber.ekvit.persistence.model.RegisterIn;
import ru.sber.ekvit.persistence.dao.ArticleDao;
import ru.sber.ekvit.persistence.dao.ContractDao;
import ru.sber.ekvit.persistence.dao.RegisterInDao;
import ru.sber.ekvit.persistence.to.RegisterInTo;

import java.util.List;

@Controller
@RequestMapping("/registerin")
@Slf4j
public class RegisterInController extends BaseToController<RegisterIn, RegisterInTo> {
    private final ContractDao contractDao;
    private final ArticleDao articleDao;
    private final RegisterInDao registerInDao;

    @Autowired
    public RegisterInController(ContractDao contractDao, RegisterInDao registerInDao, ArticleDao articleDao) {
        super(registerInDao);
        this.registerInDao = registerInDao;
        this.contractDao = contractDao;
        this.articleDao = articleDao;
    }

    @GetMapping
    public String index(Model m, @RequestParam(required = false) Integer idParam){
        log.info("index (with idParam: {}) user {}", idParam, Authorization.id());

        m.addAttribute("title", "Реестры задолженности");
        if (idParam != null){
            Contract contract = contractDao.get(idParam);
            m.addAttribute("contractName", contract.getName());
            m.addAttribute("tableCondition", "/contract/" + idParam);
        }
        return "common";
    }

    @GetMapping("/{id}")
    public String get(@PathVariable("id") Integer id, Model m){
        log.info("get id {} user {}",id, Authorization.id());

        RegisterIn registerIn = _get(id);
        List<Contract> contracts = contractDao.getAll();
        List<Article> articles = articleDao.getAll();
        m.addAttribute("articles",articles);
        m.addAttribute("contracts",contracts);
        m.addAttribute("registerIn", registerIn);
        m.addAttribute("fieldTypes", registerInDao.getFieldTypes());
        return "fragments/dialogs :: registerInDialog";
    }

    @PostMapping
    public String save(RegisterInTo entity) {
        log.info("save TO {} user {}",entity, Authorization.id());

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

        List<RegisterIn> registerIns = _getAll();
        m.addAttribute("registerIns", registerIns);
        return "fragments/tables :: registerInList";
    }

    @GetMapping("/all/contract/{id}")
    public String getAll(Model m, @PathVariable("id") Integer id){
        log.info("getAll ( with contract id: {} ) user {}", id, Authorization.id());

        List<RegisterIn> registerIns = registerInDao.getAllByContractId(id);
        m.addAttribute("registerIns", registerIns);
        return "fragments/tables :: registerInList";
    }
}
