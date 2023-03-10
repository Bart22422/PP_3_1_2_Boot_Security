package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.MyUserDetailsService;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/i")
    public String index() {
        //model.addAttribute("user", principal);
        return"us";
    }
    @GetMapping("/user")
    public String user( Model model ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getPrincipal();
        User user = (User)authentication.getPrincipal();;
        model.addAttribute("user", user);
        return "user";
    }
    @GetMapping("/admin")
    public String users(Model model){
        List<User> users = userService.getUsers();
        model.addAttribute("users", users);
        return "users";
    }
    @GetMapping("/modern-login-page")
    public String login(){
        return "modern-login-page";
    }
    @GetMapping("/add")
    public String registration(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "registration";
    }
    @PostMapping("/form")
    public String addNewUser(@RequestParam(value = "name")String name, @RequestParam(value = "password")String pass, @RequestParam(value = "lastName", required = false) String lastName, @RequestParam(value = "date",required = false, defaultValue = "0000-00-00") String date, @RequestParam(value = "authorisation",required = false, defaultValue = "ROLE_USER")String auth) throws ParseException {
        User user = new User();
        if(!date.isEmpty()){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
            simpleDateFormat.applyPattern("yyyy-MM-dd");
            Date date1 = simpleDateFormat.parse(date);
            user.setAge(date1);user.setAge(date1);
        }
        if(lastName!=null){
            user.setLastName(lastName);
        }
        user.setName(name);
        user.setPassword(pass);
        System.out.println(auth);
        user.addRole(roleService.loadByRoleName(auth));
        System.out.println(user.getRoles());
        userService.addUser(user);
        return "redirect:/";
    }
    @PostMapping("/delete")
    public String delete (@RequestParam(value = "id") long id){
        userService.deleteUser(id);
        return "redirect:/users";
    }
    @PostMapping("/clean")
    public String clean (){
        userService.cleanTable();
        return "redirect:/users";
    }
}
