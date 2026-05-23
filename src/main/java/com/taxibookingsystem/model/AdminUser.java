package com.taxibookingsystem.model;

// Inheritance — AdminUser extends User
public class AdminUser extends User {

    private String adminLevel; // SUPER or NORMAL

    public AdminUser(String userId, String username, String email,
                     String password, String adminLevel) {
        super(userId, username, email, password, "ADMIN");
        this.adminLevel = adminLevel;
    }

    // Polymorphism — override getRole()
    @Override
    public String getRole() {
        return "ADMIN (" + adminLevel + ")";
    }

    public String getAdminLevel() { return adminLevel; }
    public void setAdminLevel(String adminLevel) { this.adminLevel = adminLevel; }
}
