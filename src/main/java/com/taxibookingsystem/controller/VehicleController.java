package com.taxibookingsystem.controller;

import com.taxibookingsystem.service.VehicleService;
import com.taxibookingsystem.util.SessionManager;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService = new VehicleService();

    // READ — Admin only
    @GetMapping("/list")
    public String listVehicles(HttpSession session, Model model) {
        if (!SessionManager.isLoggedIn(session)) return "redirect:/login";
        if (!SessionManager.isAdmin(session)) return "redirect:/customer/dashboard";
        model.addAttribute("vehicles", vehicleService.getAllVehicles());
        model.addAttribute("user", SessionManager.getLoggedInUser(session));
        return "vehicle-list";
    }

    // CREATE — Admin only
    @GetMapping("/new")
    public String showAddForm(HttpSession session, Model model) {
        if (!SessionManager.isLoggedIn(session)) return "redirect:/login";
        if (!SessionManager.isAdmin(session)) return "redirect:/customer/dashboard";
        model.addAttribute("user", SessionManager.getLoggedInUser(session));
        return "vehicle-add";
    }

    @PostMapping("/create")
    public String createVehicle(@RequestParam String plateNumber,
                                @RequestParam String brand,
                                @RequestParam String model,
                                @RequestParam String type,
                                @RequestParam String status,
                                HttpSession session) {
        if (!SessionManager.isLoggedIn(session)) return "redirect:/login";
        if (!SessionManager.isAdmin(session)) return "redirect:/customer/dashboard";
        String id = "V" + System.currentTimeMillis();
        vehicleService.createVehicle(new Vehicle(id, plateNumber, brand, model, type, status));
        return "redirect:/vehicles/list";
    }

    // UPDATE — Admin only
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, HttpSession session, Model model) {
        if (!SessionManager.isLoggedIn(session)) return "redirect:/login";
        if (!SessionManager.isAdmin(session)) return "redirect:/customer/dashboard";
        model.addAttribute("vehicle", vehicleService.getVehicleById(id));
        model.addAttribute("user", SessionManager.getLoggedInUser(session));
        return "vehicle-edit";
    }

    @PostMapping("/update")
    public String updateVehicle(@RequestParam String vehicleId,
                                @RequestParam String type,
                                @RequestParam String status,
                                HttpSession session) {
        if (!SessionManager.isLoggedIn(session)) return "redirect:/login";
        if (!SessionManager.isAdmin(session)) return "redirect:/customer/dashboard";
        vehicleService.updateVehicle(vehicleId, type, status);
        return "redirect:/vehicles/list";
    }

    // DELETE — Admin only
    @GetMapping("/delete/{id}")
    public String deleteVehicle(@PathVariable String id, HttpSession session) {
        if (!SessionManager.isLoggedIn(session)) return "redirect:/login";
        if (!SessionManager.isAdmin(session)) return "redirect:/customer/dashboard";
        vehicleService.deleteVehicle(id);
        return "redirect:/vehicles/list";
    }
}
