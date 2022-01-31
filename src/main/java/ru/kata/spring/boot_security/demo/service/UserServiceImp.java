package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUsers() {

        return userRepository.findAll();
    }

    @Override
    public void save(User user) {

        userRepository.save(user);
    }

    @Override
    public void update(User updatedUser) {
        userRepository.save(updatedUser);
    }

    @Override
    public User show(Long id) {
        User newuser = null;
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            newuser = userOptional.get();
        }
        return newuser;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }


}
