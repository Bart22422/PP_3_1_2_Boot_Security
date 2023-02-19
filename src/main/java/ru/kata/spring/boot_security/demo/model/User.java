package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "usr")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String name;
    private String lastName;
    private Date age;
    @Column (name = "passwordUser")
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usr_roles",
        joinColumns = @JoinColumn(name = "usr_id"),
        inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private Set<Role> roles;

    public User(Long id, String username, String lastName, Date age, String password, Set<Role> roles) {
        this.id = id;
        this.name = username;
        this.lastName = lastName;
        this.age = age;
        this.password = password;
        this.roles = roles;
    }
    public User() {
    }


    public Set<Role> getRoles(){
        return roles;
    };

    public void setName(String name) {
        this.name = name;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setAge(Date age) {
        this.age = age;
    }
    public String getName() {
        return name;
    }
    public String getLastName() {
        return lastName;
    }
    public Date getAge() {
        return age;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }
    @Override
    public String getPassword() {
        return getPassword();
    }

    @Override
    public String getUsername() {
        return getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void addRole(Role role){
        this.roles.add(role);
    }
    @Override
    public String toString() {
        return "User" + "\n" + "name: \t" + getName() + "\n" + "last name: \t" + getLastName() + "\n" + "birthday: \t" + getAge();
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        obj = new User();
        return ((User) obj).getName().equals(this.getName()) && ((User) obj).getLastName().equals(this.getLastName()) && ((User) obj).getAge().equals(this.getAge());
    }
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (name == null ? 0 : name.hashCode());
        result = 31 * result + (age == null ? 0 : age.hashCode());
        result = 31 * result + (lastName == null ? 0 : lastName.hashCode());
        return result;
    }
}