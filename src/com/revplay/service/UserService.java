package com.revplay.service;

import com.revplay.dao.UserDAO;
import com.revplay.model.User;

public class UserService {

    private UserDAO userDAO = new UserDAO();

    /* ================= VIEW PROFILE ================= */
    public User getUserById(int userId) {
        // you can add this method in UserDAO if not present
        return null;
    }

    /* ================= UPDATE PROFILE ================= */
    public void updateProfile(int userId, String name) {
        // future use (optional)
    }
}
