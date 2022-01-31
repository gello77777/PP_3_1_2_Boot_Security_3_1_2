package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;

public interface RoleService {

    void save(Role role);

    void update(Role updatedRole);

    Role show(Long id);

    void delete(Long id);

    List<Role> getRoles();


}
