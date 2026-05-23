package com.taxibookingsystem.util;

import jakarta.servlet.http.HttpSession;

public class SessionManager {

    public static final String SESSION_USER = "loggedInUser";

    public static void setLoggedInUser(HttpSession session, User user) {
        session.setAttribute(SESSION_USER, user);
    }

    public static User getLoggedInUser(HttpSession session) {
        return (User) session.getAttribute(SESSION_USER);
    }

    public static boolean isLoggedIn(HttpSession session) {
        return session.getAttribute(SESSION_USER) != null;
    }

    public static boolean isAdmin(HttpSession session) {
        User user = getLoggedInUser(session);
        return user != null && user.getRole().equals("ADMIN");
    }

    public static void logout(HttpSession session) {
        session.invalidate();
    }
}
