package com.Demo_java_mvc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String index() {
        return "Hello wold";
    }

    @GetMapping("/user")
    public String UserPage() {
        return "Only user can access this page";
    }

    @GetMapping("/admin")
    public String AdminPage() {
        return "Only admin can access this page";
    }
}
