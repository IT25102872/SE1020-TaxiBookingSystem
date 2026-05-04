package com.taxibookingsystem.controller;

import com.taxibookingsystem.model.Booking;
import com.taxibookingsystem.service.BookingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService
            = new BookingService();

    // /bookings/list → The booking-list.html page is displayed.
    @GetMapping("/list")
    public String listBookings(Model model) {
        model.addAttribute("bookings",
                bookingService.getAllBookings());
        return "booking-list";
    }

    // /bookings → redirect to list
    @GetMapping("")
    public String home() {
        return "redirect:/bookings/list";
    }

    // The form page is displayed — GET
    @GetMapping("/new")
    public String showBookingForm() {
        return "book-ride";
    }

    // Form submit — POST
    @PostMapping("/create")
    public String createBooking(
            @RequestParam String passengerName,
            @RequestParam String pickupLocation,
            @RequestParam String dropLocation) {

        // Generate unique ID
        String id = "B" + System.currentTimeMillis();

        Booking booking = new Booking(
                id, passengerName, pickupLocation, dropLocation);

        bookingService.createBooking(booking);

        return "redirect:/bookings/list";
    }

    // Display the edit form  — GET
    @GetMapping("/edit/{id}")
    public String showEditForm(
            @PathVariable String id,
            Model model) {

        Booking booking = bookingService.getBookingById(id);
        model.addAttribute("booking", booking);
        return "update-booking";
    }

    // Update submit — POST
    @PostMapping("/update")
    public String updateBooking(
            @RequestParam String bookingId,
            @RequestParam String status) {

        bookingService.updateBookingStatus(bookingId, status);
        return "redirect:/bookings/list";
    }

    // /bookings/delete/B123 → After deleting the booking, it redirects to the list page
    @GetMapping("/delete/{id}")
    public String deleteBooking(
            @PathVariable String id) {

        bookingService.deleteBooking(id);
        return "redirect:/bookings/list";
    }
}
