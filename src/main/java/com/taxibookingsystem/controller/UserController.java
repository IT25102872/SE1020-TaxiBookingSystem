package com.taxibookingsystem.controller;

import com.taxibookingsystem.model.User;
import com.taxibookingsystem.service.UserService;
import com.taxibookingsystem.util.SessionManager;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService = new UserService();

    // READ — Admin only
    @GetMapping("/list")
    public String listUsers(HttpSession session, Model model) {
        if (!SessionManager.isLoggedIn(session)) return "redirect:/login";
        if (!SessionManager.isAdmin(session)) return "redirect:/customer/dashboard";
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user", SessionManager.getLoggedInUser(session));
        return "user-list";
    }

    // CREATE — Admin only
    @GetMapping("/new")
    public String showRegisterForm(HttpSession session) {
        if (!SessionManager.isLoggedIn(session)) return "redirect:/login";
        if (!SessionManager.isAdmin(session)) return "redirect:/customer/dashboard";
        return "user-register";
    }

    @PostMapping("/create")
    public String createUser(@RequestParam String username,
                             @RequestParam String email,
                             @RequestParam String password,
                             @RequestParam String role,
                             HttpSession session) {
        if (!SessionManager.isLoggedIn(session)) return "redirect:/login";
        if (!SessionManager.isAdmin(session)) return "redirect:/customer/dashboard";
        String id = "U" + System.currentTimeMillis();
        userService.createUser(new User(id, username, email, password, role));
        return "redirect:/users/list";
    }

    // UPDATE — Admin only
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, HttpSession session, Model model) {
        if (!SessionManager.isLoggedIn(session)) return "redirect:/login";
        if (!SessionManager.isAdmin(session)) return "redirect:/customer/dashboard";
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("loggedUser", SessionManager.getLoggedInUser(session));
        return "user-edit";
    }

    @PostMapping("/update")
    public String updateUser(@RequestParam String userId,
                             @RequestParam String email,
                             @RequestParam String role,
                             HttpSession session) {
        if (!SessionManager.isLoggedIn(session)) return "redirect:/login";
        if (!SessionManager.isAdmin(session)) return "redirect:/customer/dashboard";
        userService.updateUser(userId, email, role);
        return "redirect:/users/list";
    }

    // DELETE — Admin only
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable String id, HttpSession session) {
        if (!SessionManager.isLoggedIn(session)) return "redirect:/login";
        if (!SessionManager.isAdmin(session)) return "redirect:/customer/dashboard";
        userService.deleteUser(id);
        return "redirect:/users/list";
    }
}
