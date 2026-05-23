package com.taxibookingsystem.model;

public class User {

    // Encapsulation — private fields
    private String userId;
    private String username;
    private String email;
    private String password;
    private String role; // ADMIN or USER

    // Constructor
    public User(String userId, String username, String email, String password, String role) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User() {}

    // Getters
    public String getUserId()   { return userId; }
    public String getUsername() { return username; }
    public String getEmail()    { return email; }
    public String getPassword() { return password; }
    public String getRole()     { return role; }

    // Setters
    public void setUserId(String userId)     { this.userId = userId; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email)       { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role)         { this.role = role; }

    // File write — CSV format
    public String toFileString() {
        return userId + "," + username + "," + email + "," + password + "," + role;
    }
}
