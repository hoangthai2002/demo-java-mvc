package com.Demo_java_mvc.controller.client;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.Demo_java_mvc.domain.Product;
import com.Demo_java_mvc.service.ProductService;

@Controller
public class HomePageController {

    public final ProductService productService;

    public HomePageController(
            ProductService productService) {
        this.productService = productService;

    }

    @GetMapping("/")
    public String getHomePage(Model model) {
        List<Product> products = this.productService.fetchProducts();
        model.addAttribute("products", products);
        return "client/homepage/show";
    }

}
