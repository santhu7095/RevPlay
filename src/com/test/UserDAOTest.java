package com.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.revplay.dao.UserDAO;
import com.revplay.util.DBConnection;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserDAOTest {

    private static UserDAO userDAO;
    private static String testEmail = "testuser@revplay.com";
    private static String password = "Test@123";

    @BeforeAll
    static void setup() {
        userDAO = new UserDAO();
    }

    /* Reset DB before each test */
    @BeforeEach
    void resetUser() throws Exception {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "DELETE FROM users WHERE email=?")) {

            ps.setString(1, testEmail);
            ps.executeUpdate();
        }

        userDAO.registerUser("Test User", testEmail, password);
    }

    @Test
    @Order(1)
    void testLoginSuccess() {
        Integer id = userDAO.loginUser(testEmail, password);
        assertNotNull(id);
    }

    @Test
    @Order(2)
    void testChangePassword() {
        boolean result = userDAO.changePassword(testEmail, "Test@123", "NewPass@123");
        assertTrue(result);
    }

    @Test
    @Order(3)
    void testLoginWithNewPassword() {
        userDAO.changePassword(testEmail, "Test@123", "NewPass@123");
        Integer id = userDAO.loginUser(testEmail, "NewPass@123");
        assertNotNull(id);
    }
}
