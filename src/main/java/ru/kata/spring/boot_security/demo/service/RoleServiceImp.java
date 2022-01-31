package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImp implements RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getRoles() {

        return roleRepository.findAll();
    }


    @Override
    public void save(Role role) {

        roleRepository.save(role);
    }

    @Override
    public void update(Role updatedRole) {
        roleRepository.save(updatedRole);

    }

    @Override
    public Role show(Long id) {
        Role newrole = null;
        Optional<Role> roleOptional = roleRepository.findById(id);
        if (roleOptional.isPresent()) {
            newrole = roleOptional.get();
        }
        return newrole;
    }

    @Override
    public void delete(Long id) {
        roleRepository.deleteById(id);
    }


}
