package com.revplay.util;

import java.util.regex.Pattern;

public class ValidationUtil {

    private ValidationUtil() {
        // prevent object creation
    }

    /* ================= EMAIL VALIDATION ================= */
    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    /* ================= PASSWORD VALIDATION =================
       Rules:
       - Minimum 8 characters
       - At least 1 uppercase letter
       - At least 1 lowercase letter
       - At least 1 number
       - At least 1 special character
    */
    private static final String PASSWORD_REGEX =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()]).{8,}$";

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile(EMAIL_REGEX);

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile(PASSWORD_REGEX);

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isStrongPassword(String password) {
        return password != null && PASSWORD_PATTERN.matcher(password).matches();
    }
}
