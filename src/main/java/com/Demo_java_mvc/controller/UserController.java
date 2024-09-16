package com.Demo_java_mvc.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.Demo_java_mvc.domain.User;
import com.Demo_java_mvc.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        List<User> users = this.userService.getAllUser();

        model.addAttribute("user1", users);
        return "admin/user/table-user";
    }

    @RequestMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model, @PathVariable long id) {
        User user = this.userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("id", id);
        return "admin/user/show";
    }

    @RequestMapping("/admin/user/create") // GET
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST) // POST
    public String createUserPage(Model model, @ModelAttribute("newUser") User valueUser) {
        this.userService.handleSaveUser(valueUser);
        return "redirect:/admin/user";

    }

    @RequestMapping("/admin/user/update/{id}") // GET
    public String getUpdateUserPage(Model model, @PathVariable long id) {
        User currentUser = this.userService.getUserById(id);
        model.addAttribute("newUser", currentUser);

        return "admin/user/update";
    }

    @PostMapping("/admin/user/update") // Get
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
    public String getdeleteUserPage(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        // User user=new User();
        // user.setId(id); Lấy value
        model.addAttribute("newUser", new User());
        return "admin/user/delete";
    }

    @PostMapping("admin/user/delete")
    public String postdeleteUser(Model model, @ModelAttribute("newUser") User value) {
        this.userService.DeleteUser(value.getId());
        return "redirect:/admin/user";
    }

}
