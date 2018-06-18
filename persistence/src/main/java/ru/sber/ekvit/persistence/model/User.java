package ru.sber.ekvit.persistence.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "users")
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class User extends BaseEntity implements GrantedAuthority {

    @NonNull
    @Column(name = "name")
    private String name;

    @NonNull
    @Column(name = "password")
    private String password;

    @NonNull
    @Column(name = "email")
    private String email;

    @NonNull
    @Column(name = "role")
    private String role;

    public User(Integer id, String name, String password, String email, String role) {
        super(id);
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public User(User user) {
        this(user.getId(), user.getName(), user.getPassword(), user.getEmail(), user.getRole());
    }

    @Override
    public String getAuthority() {
        return getRole();
    }
}