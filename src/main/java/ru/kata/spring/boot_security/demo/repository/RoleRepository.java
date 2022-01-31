package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.Role;

@Service
public interface RoleRepository extends JpaRepository<Role, Long> {


}
