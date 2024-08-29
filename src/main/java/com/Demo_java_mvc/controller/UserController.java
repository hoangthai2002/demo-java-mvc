package com.Demo_java_mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Demo_java_mvc.service.UserService;

@Controller
public class UserController {

    private UserService userService;

    // // Di-> Denpendency injection
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String getHomePage() {
        return "thai.html";
    }

    // @RestController
    // public class UserController {
    // // Di-> Denpendency injection
    // private UserService userService;

    // public UserController(UserService userService) {
    // this.userService = userService;
    // }

    // @GetMapping("/")
    // public String getHomePage() {
    // return this.userService.handleHello();
    // }
}
