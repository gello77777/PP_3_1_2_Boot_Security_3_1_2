

package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.models.User;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByName(String name) {
        return userRepository.findByName(name);
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = findByName(name);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' не найден", name));
        }

        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(),
                user.getAuthorities());

    }

}


