package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

public interface RoleService {
    Role getby(Long id);
    void add(Role role);

    Role loadByRoleName(String auth);
}
