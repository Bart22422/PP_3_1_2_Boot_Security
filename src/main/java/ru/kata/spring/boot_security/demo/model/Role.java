package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;
    @Column(name = "authority")
    private String name;
    @ManyToMany(mappedBy = "roles")
    Set<User> users = new HashSet<>();

    public Role() {

    }

    public Role(String authority) {
        this.name = authority;
    }
    public Role(String authority, Set<User> users) {
        this.name = authority;
        this.users = users;
    }


    public void setName(String autority) {
        this.name = autority;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<User> getUsers() {
        return users;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getAuthority() {
        return this.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass())
            return false;

        Role role = (Role) o;
        return Objects.equals(role.getName(), this.name) && Objects.equals(role.getId(), this.id);
    }

    @Override
    public int hashCode() {
        return this.getName().hashCode() * Long.hashCode(this.getId()) * 71;
    }

    @Override
    public String toString() {
        return  name;
    }

    public void addUser(User user) {
        this.getUsers().add(user);
    }

}
