package com.Demo_java_mvc.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/admin")
    public String getdashboard() {
        return "admin/dashboard/show";
    }

}
