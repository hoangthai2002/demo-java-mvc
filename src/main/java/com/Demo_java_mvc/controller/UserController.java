package com.Demo_java_mvc.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.Demo_java_mvc.domain.User;
import com.Demo_java_mvc.repository.UserRepository;
import com.Demo_java_mvc.service.UserService;

@Controller
public class UserController {

    private final UserService userService;

    // // Di-> Denpendency injection
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String getHomePage(Model model) {
        List<User> arrayUser = this.userService.getAllUserByEmail("2@gmail.com");

        System.out.println(arrayUser);

        model.addAttribute("thai", "test");
        model.addAttribute("hoang", "hello from controler");
        return "hello";
    }

    @RequestMapping("/admin/user")
    public String getUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST)
    public String createUserPage(Model model, @ModelAttribute("newUser") User valueUser) {
        System.out.println("run here" + valueUser);
        this.userService.handleSaveUser(valueUser);
        return "hello";

    }
}
