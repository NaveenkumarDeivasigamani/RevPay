package com.revpay.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AuthServiceTest {

    private AuthService authService;

    @BeforeEach
    void setup() {
        authService = new AuthService();
    }

    @Test
    void changePassword_shouldFail_whenCurrentPasswordIsWrong() {

        int userId = 1;                 // existing user ID in DB
        String wrongPassword = "wrong";
        String newPassword = "NewPass@123";

        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.changePassword(userId, wrongPassword, newPassword)
        );

        assertEquals("Current password incorrect", exception.getMessage());
    }
}
