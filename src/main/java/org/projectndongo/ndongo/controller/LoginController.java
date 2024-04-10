package org.projectndongo.ndongo.controller;

import lombok.extern.slf4j.Slf4j;
import org.projectndongo.ndongo.domain.auth.AuthType;
import org.projectndongo.ndongo.domain.auth.User;
import org.projectndongo.ndongo.domain.auth.service.RoleService;
import org.projectndongo.ndongo.domain.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class LoginController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @RequestMapping("/register")
    public String register(Model model) {
        model.addAttribute("NewUser", new User());
        return "auth/registerForm";
    }

    @PostMapping("/makeRegister")
    public String makeRegistration(@ModelAttribute User user) {
        userService.registerUser(user);

        return "redirect:/login";
    }
}
