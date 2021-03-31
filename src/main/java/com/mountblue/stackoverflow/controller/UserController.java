package com.mountblue.stackoverflow.controller;

import com.mountblue.stackoverflow.model.User;
import com.mountblue.stackoverflow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.ManyToMany;
import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
            return "redirect:/login/signup?error";
        } else {
            user.setPassword(user.getPassword().split(",")[0]);
            userService.saveUser(user);
            return "redirect:/login/signup?success";
        }
    }

    @RequestMapping("/showLoginForm")
    public String loginPage(Model model) {
        return "user/login";
    }

    @RequestMapping("/validateUser")
    public String validateUser(@RequestParam("userName") String userName,
                               @RequestParam("password") String password) {
        if (userService.isValidUser(userName, password)) {
            return "user/home";
        } else {
            return "redirect:/login/showLoginForm";
        }
    }
}
