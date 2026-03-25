package com.eventzen.userservice.validation;

public class PasswordValidator {
    public static void validate(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$";

        if (!password.matches(pattern)) {
            throw new IllegalArgumentException(
                    "Password must be at least 8 characters long and include uppercase, lowercase, number, and special character.");
        }
    }
}