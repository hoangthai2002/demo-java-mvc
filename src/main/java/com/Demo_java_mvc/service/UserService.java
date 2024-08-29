package com.Demo_java_mvc.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    public String handleHello() {
        return "hello from service";
    }

}
