package org.projectndongo.ndongo.controller;

import org.projectndongo.ndongo.domain.auth.Role;
import org.projectndongo.ndongo.domain.auth.User;
import org.projectndongo.ndongo.domain.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/profile")
    public String profile(Model model, Authentication authentication) {
        // Get current user by Spring Security session
        model.addAttribute("EditUser", userService.getUserByUsername(authentication.getName()));
        model.addAttribute("emailS", userService.getUserByUsername(authentication.getName()).getEmail());
        model.addAttribute("roleS", userService.getUserByUsername(authentication.getName()).getRole().getId());
        return "user/profileForm";
    }

    @RequestMapping("/profile/edit/{id}")
    public String edit(@ModelAttribute("EditUser") User user, @ModelAttribute("emailS") String email, @ModelAttribute("roleS") int role, Model model) {
        userService.updateUser(user, email, role);
        model.addAttribute("success", true);
        return "redirect:/logout";
    }
}
