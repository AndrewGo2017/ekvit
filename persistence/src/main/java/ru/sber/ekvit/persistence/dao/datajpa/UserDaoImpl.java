package ru.sber.ekvit.persistence.dao.datajpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import ru.sber.ekvit.persistence.Authorization;
import ru.sber.ekvit.persistence.dao.UserDao;
import ru.sber.ekvit.persistence.dao.datajpa.crud.UserCrudDao;
import ru.sber.ekvit.persistence.model.User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Repository
@Slf4j
public class UserDaoImpl implements UserDao {
    private final UserCrudDao userCrudDao;

    @Autowired
    public UserDaoImpl(UserCrudDao userCrudDao) {
        this.userCrudDao = userCrudDao;
    }

    @Override
    public User save(User entity) {
        log.info("save {} ", entity);

        return userCrudDao.save(entity);
    }

    @Override
    public void saveAll(List<User> entities) {
        log.info("saveAll ");

        userCrudDao.saveAll(entities);
    }

    @Override
    public boolean delete(int id) {
        log.info("delete id {}", id);

        return userCrudDao.delete(id) != 0;
    }

    @Override
    public User get(int id) {
        log.info("get id {}", id);

        return userCrudDao.findById(id).orElse(null);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");

        return userCrudDao.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.info("loadUserByUsername(id) {} ",s);

        User user = get(Integer.parseInt(s));
        if (user == null)
            throw new UsernameNotFoundException("Пользователь (id)" + s + " не найден");

        return new Authorization(user);
    }

    @Override
    public UserDetails getUserDetails(String s){
        log.info("getUserDetails {}",s);

        return loadUserByUsername(s);
    }
}