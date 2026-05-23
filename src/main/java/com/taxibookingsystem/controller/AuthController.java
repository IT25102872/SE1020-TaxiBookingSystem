package com.taxibookingsystem.controller;

import com.taxibookingsystem.service.UserService;
import com.taxibookingsystem.util.SessionManager;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AuthController {

    private final UserService userService = new UserService();

    // -------- Login page --------
    @GetMapping("/login")
    public String showLoginPage(HttpSession session) {
        if (SessionManager.isLoggedIn(session)) {
            return SessionManager.isAdmin(session)
                    ? "redirect:/admin/dashboard"
                    : "redirect:/customer/dashboard";
        }
        return "login";
    }

    // -------- Login submit --------
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session, Model model) {

        List<User> users = userService.getAllUsers();
        User foundUser = null;

        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                foundUser = u;
                break;
            }
        }

        if (foundUser != null) {
            SessionManager.setLoggedInUser(session, foundUser);
            return foundUser.getRole().equals("ADMIN")
                    ? "redirect:/admin/dashboard"
                    : "redirect:/customer/dashboard";
        } else {
            model.addAttribute("error", "Invalid username or password!");
            return "login";
        }
    }

    // -------- Logout --------
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        SessionManager.logout(session);
        return "redirect:/login";
    }

    // -------- Admin Dashboard --------
    @GetMapping("/admin/dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        if (!SessionManager.isLoggedIn(session)) return "redirect:/login";
        if (!SessionManager.isAdmin(session)) return "redirect:/customer/dashboard";
        model.addAttribute("user", SessionManager.getLoggedInUser(session));
        return "admin-dashboard";
    }

    // -------- Customer Dashboard --------
    @GetMapping("/customer/dashboard")
    public String customerDashboard(HttpSession session, Model model) {
        if (!SessionManager.isLoggedIn(session)) return "redirect:/login";
        model.addAttribute("user", SessionManager.getLoggedInUser(session));
        return "customer-dashboard";
    }

    // -------- Home redirect --------
    @GetMapping("/")
    public String home(HttpSession session) {
        if (SessionManager.isLoggedIn(session)) {
            return SessionManager.isAdmin(session)
                    ? "redirect:/admin/dashboard"
                    : "redirect:/customer/dashboard";
        }
        return "redirect:/login";
    }
}
