package com.taxibookingsystem.controller;

import com.taxibookingsystem.service.PaymentService;
import com.taxibookingsystem.util.SessionManager;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService = new PaymentService();

    // READ — Admin + Customer
    @GetMapping("/list")
    public String listPayments(HttpSession session, Model model) {
        if (!SessionManager.isLoggedIn(session)) return "redirect:/login";
        model.addAttribute("payments", paymentService.getAllPayments());
        model.addAttribute("user", SessionManager.getLoggedInUser(session));
        model.addAttribute("isAdmin", SessionManager.isAdmin(session));
        return "payment-list";
    }

    // CREATE — Admin only
    @GetMapping("/new")
    public String showAddForm(HttpSession session, Model model) {
        if (!SessionManager.isLoggedIn(session)) return "redirect:/login";
        if (!SessionManager.isAdmin(session)) return "redirect:/customer/dashboard";
        model.addAttribute("user", SessionManager.getLoggedInUser(session));
        return "payment-add";
    }

    @PostMapping("/create")
    public String createPayment(@RequestParam String bookingId,
                                @RequestParam String passengerName,
                                @RequestParam double amount,
                                @RequestParam String method,
                                @RequestParam String status,
                                HttpSession session) {
        if (!SessionManager.isLoggedIn(session)) return "redirect:/login";
        if (!SessionManager.isAdmin(session)) return "redirect:/customer/dashboard";
        String id = "P" + System.currentTimeMillis();
        paymentService.createPayment(new Payment(id, bookingId, passengerName, amount, method, status));
        return "redirect:/payments/list";
    }

    // UPDATE — Admin only
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, HttpSession session, Model model) {
        if (!SessionManager.isLoggedIn(session)) return "redirect:/login";
        if (!SessionManager.isAdmin(session)) return "redirect:/customer/dashboard";
        model.addAttribute("payment", paymentService.getPaymentById(id));
        model.addAttribute("user", SessionManager.getLoggedInUser(session));
        return "payment-edit";
    }

    @PostMapping("/update")
    public String updatePayment(@RequestParam String paymentId,
                                @RequestParam String method,
                                @RequestParam String status,
                                HttpSession session) {
        if (!SessionManager.isLoggedIn(session)) return "redirect:/login";
        if (!SessionManager.isAdmin(session)) return "redirect:/customer/dashboard";
        paymentService.updatePayment(paymentId, method, status);
        return "redirect:/payments/list";
    }

    // DELETE — Admin only
    @GetMapping("/delete/{id}")
    public String deletePayment(@PathVariable String id, HttpSession session) {
        if (!SessionManager.isLoggedIn(session)) return "redirect:/login";
        if (!SessionManager.isAdmin(session)) return "redirect:/customer/dashboard";
        paymentService.deletePayment(id);
        return "redirect:/payments/list";
    }
}
