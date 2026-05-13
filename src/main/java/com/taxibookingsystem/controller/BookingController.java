package com.taxibookingsystem.controller;

import com.taxibookingsystem.model.Booking;
import com.taxibookingsystem.service.BookingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService = new BookingService();

    // READ — Admin + Customer
    @GetMapping("/list")
    public String listBookings(HttpSession session, Model model) {
        if (!SessionManager.isLoggedIn(session)) return "redirect:/login";
        model.addAttribute("bookings", bookingService.getAllBookings());
        model.addAttribute("user", SessionManager.getLoggedInUser(session));
        model.addAttribute("isAdmin", SessionManager.isAdmin(session));
        return "booking-list";
    }

    // CREATE — Admin + Customer
    @GetMapping("/new")
    public String showBookingForm(HttpSession session) {
        if (!SessionManager.isLoggedIn(session)) return "redirect:/login";
        return "book-ride";
    }

    @PostMapping("/create")
    public String createBooking(@RequestParam String passengerName,
                                @RequestParam String pickupLocation,
                                @RequestParam String dropLocation,
                                HttpSession session) {
        if (!SessionManager.isLoggedIn(session)) return "redirect:/login";
        String id = "B" + System.currentTimeMillis();
        Booking booking = new Booking(id, passengerName, pickupLocation, dropLocation);
        bookingService.createBooking(booking);
        return "redirect:/bookings/list";
    }

    // UPDATE — Admin only
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, HttpSession session, Model model) {
        if (!SessionManager.isLoggedIn(session)) return "redirect:/login";
        if (!SessionManager.isAdmin(session)) return "redirect:/customer/dashboard";
        model.addAttribute("booking", bookingService.getBookingById(id));
        model.addAttribute("user", SessionManager.getLoggedInUser(session));
        return "update-booking";
    }

    @PostMapping("/update")
    public String updateBooking(@RequestParam String bookingId,
                                @RequestParam String status,
                                HttpSession session) {
        if (!SessionManager.isLoggedIn(session)) return "redirect:/login";
        if (!SessionManager.isAdmin(session)) return "redirect:/customer/dashboard";
        bookingService.updateBookingStatus(bookingId, status);
        return "redirect:/bookings/list";
    }

    // DELETE — Admin only
    @GetMapping("/delete/{id}")
    public String deleteBooking(@PathVariable String id, HttpSession session) {
        if (!SessionManager.isLoggedIn(session)) return "redirect:/login";
        if (!SessionManager.isAdmin(session)) return "redirect:/customer/dashboard";
        bookingService.deleteBooking(id);
        return "redirect:/bookings/list";
    }
}
