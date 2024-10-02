package com.Demo_java_mvc.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.Demo_java_mvc.domain.Product;
import com.Demo_java_mvc.service.ProductService;

@Controller
public class ItemController {
    public final ProductService productService;

    public ItemController(
            ProductService productService) {
        this.productService = productService;

    }

    @GetMapping("/product/{id}")
    public String getProductPage(Model model, @PathVariable long id) {
        Product pr = this.productService.fetchProductById(id).get();
        model.addAttribute("product", pr);
        model.addAttribute("id", id);

        return "client/product/detail";
    }

}
