package com.Demo_java_mvc.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {

    @GetMapping("/admin/order")
    public String getOrderController() {
        return "admin/order/showOrder";
    }

}
