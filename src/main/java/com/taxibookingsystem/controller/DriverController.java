package com.taxibookingsystem.controller;

import com.taxibookingsystem.model.Driver;
import com.taxibookingsystem.service.DriverService;
import com.taxibookingsystem.util.SessionManager;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/drivers")
public class DriverController {

    private final DriverService driverService = new DriverService();

    // READ — Admin only
    @GetMapping("/list")
    public String listDrivers(HttpSession session, Model model) {
        if (!SessionManager.isLoggedIn(session)) return "redirect:/login";
        if (!SessionManager.isAdmin(session)) return "redirect:/customer/dashboard";
        model.addAttribute("drivers", driverService.getAllDrivers());
        model.addAttribute("user", SessionManager.getLoggedInUser(session));
        return "driver-list";
    }

    // CREATE — Admin only
    @GetMapping("/new")
    public String showAddForm(HttpSession session) {
        if (!SessionManager.isLoggedIn(session)) return "redirect:/login";
        if (!SessionManager.isAdmin(session)) return "redirect:/customer/dashboard";
        return "driver-add";
    }

    @PostMapping("/create")
    public String createDriver(@RequestParam String name,
                               @RequestParam String phone,
                               @RequestParam String licenseNumber,
                               @RequestParam String status,
                               HttpSession session) {
        if (!SessionManager.isLoggedIn(session)) return "redirect:/login";
        if (!SessionManager.isAdmin(session)) return "redirect:/customer/dashboard";
        String id = "D" + System.currentTimeMillis();
        driverService.createDriver(new Driver(id, name, phone, licenseNumber, status));
        return "redirect:/drivers/list";
    }

    // UPDATE — Admin only
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, HttpSession session, Model model) {
        if (!SessionManager.isLoggedIn(session)) return "redirect:/login";
        if (!SessionManager.isAdmin(session)) return "redirect:/customer/dashboard";
        model.addAttribute("driver", driverService.getDriverById(id));
        model.addAttribute("user", SessionManager.getLoggedInUser(session));
        return "driver-edit";
    }

    @PostMapping("/update")
    public String updateDriver(@RequestParam String driverId,
                               @RequestParam String phone,
                               @RequestParam String status,
                               HttpSession session) {
        if (!SessionManager.isLoggedIn(session)) return "redirect:/login";
        if (!SessionManager.isAdmin(session)) return "redirect:/customer/dashboard";
        driverService.updateDriver(driverId, phone, status);
        return "redirect:/drivers/list";
    }

    // DELETE — Admin only
    @GetMapping("/delete/{id}")
    public String deleteDriver(@PathVariable String id, HttpSession session) {
        if (!SessionManager.isLoggedIn(session)) return "redirect:/login";
        if (!SessionManager.isAdmin(session)) return "redirect:/customer/dashboard";
        driverService.deleteDriver(id);
        return "redirect:/drivers/list";
    }
}
