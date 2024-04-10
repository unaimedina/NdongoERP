package org.projectndongo.ndongo.controller;

import org.projectndongo.ndongo.domain.auth.User;
import org.projectndongo.ndongo.domain.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "main/users-list";
    }

    @GetMapping("/admin")
    public String countUsers(Model model) {
        int userCount = userService.countUsers();
        long countPlayers = userService.countPlayers();
        long countAdmins = userService.countAdmins();
        long countProjectManagers = userService.countProjectManagers();

        model.addAttribute("userCount", userCount);
        model.addAttribute("countPlayers", countPlayers);
        model.addAttribute("countAdmins", countAdmins);
        model.addAttribute("countProjectManagers", countProjectManagers);

        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "main/dashboard";
    }

    @GetMapping("/showUsers")
    public String showUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "main/users-list";
    }

    @GetMapping("/showAdmins")
    public String showAdmins(Model model) {
        List<User> admins = userService.getUsersByRole("admin");
        model.addAttribute("users", admins);
        return "main/users-list";
    }

    @GetMapping("/filterByRole")
    public String filterByRole(@RequestParam("role") String role, Model model) {
        List<User> filteredUsers = userService.getUsersByRole(role);
        model.addAttribute("users", filteredUsers);
        return "main/users-list";
    }
}
