package ru.sber.ekvit.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.sber.ekvit.job.JobType;
import ru.sber.ekvit.job.register.ClientRegister;
import ru.sber.ekvit.persistence.Authorization;
import ru.sber.ekvit.persistence.dao.BaseDao;
import ru.sber.ekvit.persistence.model.Contract;

import java.util.List;

@Controller
@RequestMapping("/register-in-upload")
@Slf4j
public class RegisterFuncController extends BaseController<Contract> {
    private final ClientRegister clientRegister;

    @Autowired
    public RegisterFuncController(BaseDao<Contract> dao, ClientRegister clientRegister) {
        super(dao);
        this.clientRegister = clientRegister;
    }

    @GetMapping
    public String index(Model m) {
        log.info("index user {}", Authorization.id());

        List<Contract> contracts = _getAll();
        m.addAttribute("noEdit", "");
        m.addAttribute("contracts", contracts);
        m.addAttribute("jobType", JobType.values());
        m.addAttribute("title", "Реестры задолженности");
        return "register-in-upload";
    }

    @PostMapping
    public String upload(@RequestParam("files") MultipartFile[] files, @RequestParam("contract") String contract, @RequestParam("jobType") JobType jobType, Model m) {
        log.info("upload file {} with ( contract: {}, jobType: {} ) user {}", files, contract, jobType, Authorization.id());

        try{
            clientRegister.handle(files, contract, jobType);
            m.addAttribute("status", "1");
        } catch (Exception e){
            m.addAttribute("status", e.getMessage());
        }

        return index(m);
    }
}