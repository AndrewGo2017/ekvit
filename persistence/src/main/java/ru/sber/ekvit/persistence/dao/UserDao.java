package ru.sber.ekvit.persistence.dao;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.sber.ekvit.persistence.model.User;

public interface UserDao extends BaseDao<User>, UserDetailsService {
    UserDetails getUserDetails(String s);
}
