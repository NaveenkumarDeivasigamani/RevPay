package com.revpay.app;


/**
 * Application entry point.
 * Starts the RevPay console application.
 */
public class RevPayApplication {

    public static void main(String[] args) {

        // Start menu-driven navigation
        MenuHandler menuHandler = new MenuHandler();
        menuHandler.start();
    }
}
