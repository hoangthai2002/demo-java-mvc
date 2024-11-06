package com.Demo_java_mvc.controller.client;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.Demo_java_mvc.domain.Product;
import com.Demo_java_mvc.domain.User;
import com.Demo_java_mvc.domain.dto.RegisterDTO;
import com.Demo_java_mvc.service.ProductService;
import com.Demo_java_mvc.service.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomePageController {

    public final ProductService productService;
    public final UserService userService;
    public final PasswordEncoder passwordEncoder;

    public HomePageController(
            ProductService productService,
            UserService userService,
            PasswordEncoder passwordEncoder)

    {
        this.productService = productService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;

    }

    @GetMapping("/")
    public String getHomePage(Model model, @Param("keyword") String keyword) {
        List<Product> products = this.productService.fetchProducts();

        model.addAttribute("products", products);
        return "client/homepage/show";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("registerUser", new RegisterDTO());
        return "client/auth/register";

    }

    @PostMapping("/register")
    public String handleRegister(@ModelAttribute("registerUser") @Valid RegisterDTO registerDTO,
            BindingResult bindingResult) {

        // validate
        if (bindingResult.hasErrors()) {
            return "client/auth/register";
        }

        User user = this.userService.registerDTOtoUser(registerDTO);

        String hashPassword = this.passwordEncoder.encode(user.getPassword());

        user.setPassword(hashPassword);
        user.setRole(this.userService.getRoleByName("USER"));
        // Save User
        this.userService.handleSaveUser(user);
        return "redirect:/login";

    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {

        return "client/auth/login";
    }

    @GetMapping("/access")
    public String getAccessPage(Model model) {

        return "client/auth/access";

    }
}
