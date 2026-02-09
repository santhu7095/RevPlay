package com.revplay.service;

import java.util.Scanner;

import com.revplay.dao.UserDAO;
import com.revplay.dao.ArtistDAO;
import com.revplay.model.Artist;
import com.revplay.model.UserType;
import com.revplay.util.PasswordUtil;
import com.revplay.util.ValidationUtil;

public class AuthService {

    private UserDAO userDAO = new UserDAO();
    private ArtistDAO artistDAO = new ArtistDAO();

    /* ================= REGISTER ================= */
    public void register(UserType type, String name, String email,
                         String password, String bio, String genre) {

        // ✅ Email validation
        if (!ValidationUtil.isValidEmail(email)) {
            System.out.println("Invalid email format!");
            return;
        }

        // ✅ Password strength validation
        if (!ValidationUtil.isStrongPassword(password)) {
            System.out.println(
                "Password must be at least 8 characters and include uppercase, lowercase, number, and special character."
            );
            return;
        }

        String hashedPassword = PasswordUtil.hashPassword(password);
        boolean success;

        if (type == UserType.USER) {
            success = userDAO.registerUser(name, email, hashedPassword);
        } else {
            Artist artist = new Artist();
            artist.setArtistName(name);
            artist.setEmail(email);
            artist.setPassword(hashedPassword);
            artist.setBio(bio);
            artist.setGenre(genre);

            success = artistDAO.registerArtist(artist);
        }

        if (success) {
            System.out.println("Registration successful!");
        } else {
            System.out.println("Registration failed!");
        }
    }

    /* ================= LOGIN ================= */
    public Integer login(UserType type, String email, String password) {

        String hashedPassword = PasswordUtil.hashPassword(password);
        Integer id;

        if (type == UserType.USER) {
            id = userDAO.loginUser(email, hashedPassword);
        } else {
            id = artistDAO.loginArtist(email, hashedPassword);
        }

        if (id != null) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid email or password!");
        }

        return id;
    }

    /* ================= FORGOT PASSWORD ================= */
    public void forgotPassword(UserType type, Scanner sc) {

        System.out.print("Enter registered email: ");
        String email = sc.nextLine();

        boolean exists;

        if (type == UserType.USER) {
            exists = userDAO.emailExists(email);
        } else {
            exists = artistDAO.emailExists(email);
        }

        if (!exists) {
            System.out.println("Invalid email or account does not exist.");
            return;
        }

        System.out.print("Enter new password: ");
        String newPassword = sc.nextLine();

        // ✅ Password strength validation
        if (!ValidationUtil.isStrongPassword(newPassword)) {
            System.out.println(
                "Password must be at least 8 characters and include uppercase, lowercase, number, and special character."
            );
            return;
        }

        System.out.print("Confirm new password: ");
        String confirmPassword = sc.nextLine();

        if (!newPassword.equals(confirmPassword)) {
            System.out.println("Passwords do not match!");
            return;
        }

        String hashedPassword = PasswordUtil.hashPassword(newPassword);
        boolean updated;

        if (type == UserType.USER) {
            updated = userDAO.resetPassword(email, hashedPassword);
        } else {
            updated = artistDAO.resetPassword(email, hashedPassword);
        }

        if (updated) {
            System.out.println("Password reset successful!");
        } else {
            System.out.println("Failed to reset password.");
        }
    }
}
