package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getby(Long id) {
        return roleRepository.getById(id);
    }

    @Override
    public void add(Role role) {
        roleRepository.save(role);
    }
   public Role loadByRoleName(String s) {
        Optional<Role> role = roleRepository.findByName(s);
        if(role.isEmpty()){
            Role role1 = new Role(s);
            roleRepository.save(role1);
            return role1;
        }
        return role.get();
    }

}
