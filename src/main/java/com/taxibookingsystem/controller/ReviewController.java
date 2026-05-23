package com.taxibookingsystem.controller;

import com.taxibookingsystem.service.ReviewService;
import com.taxibookingsystem.util.SessionManager;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService = new ReviewService();

    // READ — Admin + Customer
    @GetMapping("/list")
    public String listReviews(HttpSession session, Model model) {
        if (!SessionManager.isLoggedIn(session)) return "redirect:/login";
        model.addAttribute("reviews", reviewService.getAllReviews());
        model.addAttribute("user", SessionManager.getLoggedInUser(session));
        model.addAttribute("isAdmin", SessionManager.isAdmin(session));
        return "review-list";
    }

    // CREATE — Admin + Customer
    @GetMapping("/new")
    public String showAddForm(HttpSession session, Model model) {
        if (!SessionManager.isLoggedIn(session)) return "redirect:/login";
        model.addAttribute("user", SessionManager.getLoggedInUser(session));
        return "review-add";
    }

    @PostMapping("/create")
    public String createReview(@RequestParam String bookingId,
                               @RequestParam String passengerName,
                               @RequestParam int rating,
                               @RequestParam String comment,
                               HttpSession session) {
        if (!SessionManager.isLoggedIn(session)) return "redirect:/login";
        String id = "R" + System.currentTimeMillis();
        // Customer ලෙස PENDING — Admin ලෙස APPROVED
        String status = SessionManager.isAdmin(session) ? "APPROVED" : "PENDING";
        reviewService.createReview(new Review(id, bookingId, passengerName, rating, comment, status));
        return "redirect:/reviews/list";
    }

    // UPDATE — Admin only
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, HttpSession session, Model model) {
        if (!SessionManager.isLoggedIn(session)) return "redirect:/login";
        if (!SessionManager.isAdmin(session)) return "redirect:/customer/dashboard";
        model.addAttribute("review", reviewService.getReviewById(id));
        model.addAttribute("user", SessionManager.getLoggedInUser(session));
        return "review-edit";
    }

    @PostMapping("/update")
    public String updateReview(@RequestParam String reviewId,
                               @RequestParam String comment,
                               @RequestParam String status,
                               HttpSession session) {
        if (!SessionManager.isLoggedIn(session)) return "redirect:/login";
        if (!SessionManager.isAdmin(session)) return "redirect:/customer/dashboard";
        reviewService.updateReview(reviewId, comment, status);
        return "redirect:/reviews/list";
    }

    // DELETE — Admin only
    @GetMapping("/delete/{id}")
    public String deleteReview(@PathVariable String id, HttpSession session) {
        if (!SessionManager.isLoggedIn(session)) return "redirect:/login";
        if (!SessionManager.isAdmin(session)) return "redirect:/customer/dashboard";
        reviewService.deleteReview(id);
        return "redirect:/reviews/list";
    }
}
