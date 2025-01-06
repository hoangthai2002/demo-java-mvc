package com.Demo_java_mvc.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.Demo_java_mvc.domain.User;
import com.Demo_java_mvc.service.UploadService;
import com.Demo_java_mvc.service.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    private final UserService userService;
    private final UploadService uploadService;
    private final PasswordEncoder passwordEncoder;

    // // Di-> Denpendency injection
    public UserController(
            UserService userService,
            UploadService uploadService,
            PasswordEncoder passwordEncode) {
        this.userService = userService;
        this.passwordEncoder = passwordEncode;
        this.uploadService = uploadService;
    }

    // @RequestMapping("/")
    // public String getHomePage(Model model) {
    // List<User> arrayUser = this.userService.getAllUserByEmail("2@gmail.com");

    // System.out.println(arrayUser);

    // model.addAttribute("thai", "test");
    // model.addAttribute("hoang", "hello from controler");
    // return "hello";
    // }

    @RequestMapping("/admin/user")
    public String getUserPage(Model model, @RequestParam("page") Optional<String> pageOption) {
        int page = 1;
        try {
            if (pageOption.isPresent()) {
                page = Integer.parseInt(pageOption.get());
            } else {

            }
        } catch (Exception e) {

        }
        Pageable pageable = PageRequest.of(page - 1, 2);
        Page<User> users = this.userService.getAllUser(pageable);
        List<User> listUser = users.getContent();

        model.addAttribute("user1", listUser);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", users.getTotalPages());
        return "admin/user/show";
    }

    @RequestMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model, @PathVariable long id) {
        User user = this.userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("id", id);
        return "admin/user/detail";
    }

    @GetMapping("/admin/user/create") // GET
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    // tạo mới người dùng
    @PostMapping("/admin/user/create") // POST
    public String createUserPage(Model model,
            @ModelAttribute("newUser") @Valid User valueUser,
            BindingResult newUserBindingResult,
            @RequestParam("File") MultipartFile file) {

        // validate
        List<FieldError> errors = newUserBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(error.getField() + " - " + error.getDefaultMessage());
        }

        // vilidate
        if (newUserBindingResult.hasErrors()) {
            return "admin/user/create";

        }
        // Upload image
        String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");
        String hashPassword = this.passwordEncoder.encode(valueUser.getPassword());
        valueUser.setAvatar(avatar);
        valueUser.setPassword(hashPassword);
        valueUser.setRole(this.userService.getRoleByName(valueUser.getRole().getName()));
        this.userService.handleSaveUser(valueUser);
        return "redirect:/admin/user";

    }

    @RequestMapping("/admin/user/update/{id}")
    public String getUpdateUserPage(Model model, @PathVariable long id) {
        User currentUser = this.userService.getUserById(id);
        model.addAttribute("newUser", currentUser);
        return "admin/user/update";

    }

    @PostMapping("/admin/user/update")
    public String postUpdateUser(Model model, @ModelAttribute("newUser") User valueUser) {
        User currentUser = this.userService.getUserById(valueUser.getId());
        if (currentUser != null) {
            currentUser.setAddress(valueUser.getAddress());
            currentUser.setFullName(valueUser.getFullName());
            currentUser.setPhone(valueUser.getPhone());
            this.userService.handleSaveUser(currentUser);// đẩy data

        }
        return "redirect:/admin/user";
    }

    @GetMapping("admin/user/delete/{id}")
    public String getDeleteUserPage(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        // User user=new User();
        // user.setId(id); Lấy value
        model.addAttribute("newUser", new User());
        return "admin/user/delete";
    }

    @PostMapping("admin/user/delete")
    public String postDeleteUser(Model model, @ModelAttribute("newUser") User value) {
        this.userService.DeleteUser(value.getId());
        return "redirect:/admin/user";
    }

}
