package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserDetailsServiceImp;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    private UserService userService;
    private RoleService roleService;
    private UserDetailsServiceImp userDetailsServiceImp;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setUserDetailsServiceImp(UserDetailsServiceImp userDetailsServiceImp) {
        this.userDetailsServiceImp = userDetailsServiceImp;
    }

    @PostConstruct
    public void init() {
        Role role1 = new Role("ROLE_USER");
        Role role2 = new Role("ROLE_ADMIN");
        roleService.save(role1);
        roleService.save(role2);
        List<Role> roles = new ArrayList<>();
        roles.add(role2);
        User user1 = new User("admin", null, null, "admin", roles);
        userService.save(user1);

    }

    @GetMapping("/admin/users")

    public String getUsers(Model model) {
        List<User> userlist = userService.getUsers();
        model.addAttribute("keyValue", userlist);

        return "usersView";
    }

    @GetMapping("/admin/users/new")
    public String newCar(Model model) {
        List <Role> rolesList = roleService.getRoles();
        System.out.println(rolesList);
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("rolesList", rolesList);
        return "new";
    }

    @PostMapping("/admin/users")
    public String create(@ModelAttribute("user") User user,
                         @RequestParam(value = "rolesIdArr" , required = false) int[] rolesIdArr) {
        List <Role> userRoles = new ArrayList<>();

        if (rolesIdArr != null) {
            for (int i : rolesIdArr) {
                userRoles.add(roleService.show((long) i));
            }
        }
        user.setRoles(userRoles);
        userService.save(user);

        return "redirect:/admin/users/";
    }

    @GetMapping("/admin/users/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        User user1 = userService.show(id);
        List <Role> rolesList = roleService.getRoles();
        model.addAttribute("rolesList", rolesList);
        model.addAttribute("user", user1);
        return "edit";
    }

    @PatchMapping("/admin/users/{id}")
    public String update(@ModelAttribute("user") User user,
                         @RequestParam(value = "rolesIdArr" , required = false) int[] rolesIdArr) {
        List <Role> userRoles = new ArrayList<>();
        if (rolesIdArr != null) {
            for (int i : rolesIdArr) {
                userRoles.add(roleService.show((long) i));
            }
        }
        user.setRoles(userRoles);
        userService.save(user);
        return "redirect:/admin/users/";
    }

    @GetMapping("/admin/users/{id}/delete")
    public String delete(Model model, @PathVariable("id") long id) {
        User user1 = userService.show(id);
        model.addAttribute("user", user1);
        return "delete";
    }

    @DeleteMapping("/admin/users/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/admin/users/";
    }


    @GetMapping("/authenticated")
    public String pageForAuthenticatedUsers(Principal principal) {
        User user = userDetailsServiceImp.findByName(principal.getName());
        return "secured part of web service: " + user.getName() + "  " + user.getCity();
    }


    @GetMapping("/user")
    public String pageForUsers(Principal principal, Model model) {
        User user = userDetailsServiceImp.findByName(principal.getName());
        model.addAttribute(user);

        return "userForm";
    }

    @GetMapping("/admin")
    public String pageRedirect(Principal principal) {

        return "redirect:/admin/users/";
    }

}