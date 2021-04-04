package com.mountblue.stackoverflow.controller;

import com.mountblue.stackoverflow.model.User;
import com.mountblue.stackoverflow.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/showHomePage")
    public String showHomePage(@ModelAttribute("user") User user, Model model) {
        System.out.println(user);
        model.addAttribute("user", user);
        return "user/home";
    }

    @RequestMapping("/showSignupForm")
    public String showRegistrationForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "user/register";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") @Valid User user,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/user/showSignupForm?error";
        } else {
            userService.saveUser(user);
            return "redirect:/user/showSignupForm?success";
        }
    }

    @RequestMapping("/showLoginForm")
    public String loginPage(Model model) {
        return "user/login";
    }

    @RequestMapping("/validateUser")
    public String validateUser(@RequestParam("userName") String email,
                               @RequestParam("password") String password, Model model) {
        if (userService.isValidUser(email, password)) {
            User user = userService.getUserByEmail(email);
            model.addAttribute("user", user);
            return "redirect:/user/showHomePage?success";
        } else {
            return "redirect:/user/showLoginForm?error";
        }
    }
}
