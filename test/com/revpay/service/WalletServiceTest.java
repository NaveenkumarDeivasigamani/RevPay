package com.revpay.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class WalletServiceTest {

    private final WalletService walletService = new WalletService();

    @Test
    void addMoney_shouldFail_forNegativeAmount() {
        Exception ex = assertThrows(
            IllegalArgumentException.class,
            () -> walletService.addMoneyViaPaymentMethod(1, -100)
        );

        assertEquals("Amount must be greater than zero", ex.getMessage());
    }
}
