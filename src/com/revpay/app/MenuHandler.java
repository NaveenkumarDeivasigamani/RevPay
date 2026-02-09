package com.revpay.app;

import java.util.Scanner;

import com.revpay.controller.*;
import com.revpay.model.User;


public class MenuHandler {
	
	private final Scanner scanner = new Scanner(System.in);
    
    private final AuthController authController = new AuthController();
    private final WalletController walletController = new WalletController();
    private final TransactionController transactionController = new TransactionController();
    private final PaymentMethodController paymentMethodController = new PaymentMethodController();
    private final InvoiceController invoiceController = new InvoiceController();
    private final LoanController loanController = new LoanController();
    private final AnalyticsController analyticsController = new AnalyticsController();
    private final MoneyRequestController moneyRequestController = new MoneyRequestController();
    private final NotificationController notificationController = new NotificationController();
    private final BusinessAccountController businessAccountController = new BusinessAccountController();
    
    
    public void start() {

        while (true) {
            
        	System.out.println("\n=== Welcome to RevPay ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Forgot Password");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    registrationMenu();
                    break;
                case 2:
                    loginMenu();
                    break;
                case 3:
                    forgotPasswordMenu();
                    break;
                case 4:
                    System.out.println("Thank you for using RevPay");
                    System.exit(0);
                default:
                    System.out.println("Invalid option");
            }
        }
    }
    
    
    /* ================= REGISTRATION ================= */
    
    private void registrationMenu() {

        System.out.println("\n1. Personal Account");
        System.out.println("2. Business Account");
        System.out.print("Choose account type: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            registerPersonal();
        } else if (choice == 2) {
            registerBusiness();
        } else {
            System.out.println("Invalid option");
        }
    }
    
    
    private void registerPersonal() {

        System.out.print("Full Name: ");
        String name = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Phone: ");
        String phone = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print("Transaction PIN: ");
        String pin = scanner.nextLine();
        
        System.out.println("Choose a security question:");
        System.out.println("1. What is your mother’s maiden name?");
        System.out.println("2. What was your first school?");
        System.out.println("3. What is your favorite color?");
        System.out.print("Enter choice (1-3): ");
        
        int questionChoice = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        String securityQuestion = getSecurityQuestion(questionChoice);
        
        System.out.print("Your Answer: ");
        String securityAnswer = scanner.nextLine();

        authController.registerPersonal(name, email, phone, password, pin, securityQuestion, securityAnswer);
    }
    
    
    private void registerBusiness() {

        System.out.print("Full Name: ");
        String name = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Phone: ");
        String phone = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print("Transaction PIN: ");
        String pin = scanner.nextLine();
        
        System.out.println("Choose a security question:");
        System.out.println("1. What is your mother’s maiden name?");
        System.out.println("2. What was your first school?");
        System.out.println("3. What is your favorite color?");
        System.out.print("Enter choice (1-3): ");

        int questionChoice = scanner.nextInt();
        scanner.nextLine();
        
        String securityQuestion = getSecurityQuestion(questionChoice);

        System.out.print("Your Answer: ");
        String securityAnswer = scanner.nextLine();
        
        // CREATE USER VIA AUTH
        int userId = authController.registerBusinessUser(
            name, email, phone,
            password, pin,
            securityQuestion, securityAnswer
        );

        if (userId == -1) {
            return;
        }
        
        // ADD BUSINESS DETAILS
        System.out.print("Business Name: ");
        String businessName = scanner.nextLine();

        System.out.print("Business Type: ");
        String businessType = scanner.nextLine();

        System.out.print("Tax ID: ");
        String taxId = scanner.nextLine();

        System.out.print("Address: ");
        String address = scanner.nextLine();

        businessAccountController.addBusinessDetails(userId, businessName, businessType, taxId, address);    
   }
    
    
    private void forgotPasswordMenu() {

        System.out.print("Enter Email / Phone: ");
        String login = scanner.nextLine();

        String question = authController.getSecurityQuestion(login);

        if (question == null) {
            return;
        }

        System.out.println("Security Question:");
        System.out.println(question);

        System.out.print("Your Answer: ");
        String answer = scanner.nextLine();

        System.out.print("New Password: ");
        String newPassword = scanner.nextLine();

        authController.forgotPassword(login, answer, newPassword);
    }

    
    
    /* ================= LOGIN ================= */
    
    private void loginMenu() {

        System.out.print("Email / Phone: ");
        String login = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = authController.login(login, password);

        if (user == null) {
            System.out.println("Login failed");
            return;
        }

        if ("BUSINESS".equals(user.getAccountType())) {
            businessMenu(user);
        } else {
            personalMenu(user);
        }
    }
    
    /* ================= PERSONAL MENU ================= */
    
    private void personalMenu(User user) {

        while (true) {
            System.out.println("\n=== Personal Menu ===");
            System.out.println("1. View Wallet");
            System.out.println("2. Add Payment Method");
            System.out.println("3. Add Money (via Payment Method)");
            System.out.println("4. Transfer Money");
            System.out.println("5. Request Money");
            System.out.println("6. View Transactions");
            System.out.println("7. Notifications");
            System.out.println("8. Money Requests");
            System.out.println("9. Change Password");
            System.out.println("10. Logout");
            
            System.out.print("Choose option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
            case 1:
                walletController.viewWallet(user.getUserId());
                break;

            case 2:
                System.out.print("Card Number: ");
                String card = scanner.nextLine();

                System.out.print("Card Holder Name: ");
                String holderName = scanner.nextLine();

                System.out.print("Expiry Month (MM): ");
                int month = scanner.nextInt();

                System.out.print("Expiry Year (YYYY): ");
                int year = scanner.nextInt();
                scanner.nextLine();

                System.out.print("Set as default card? (true/false): ");
                boolean makeDefault = scanner.nextBoolean();
                scanner.nextLine();

                paymentMethodController.addPaymentMethod(
                        user.getUserId(),
                        card,
                        holderName,
                        month,
                        year,
                        makeDefault
                );
                break;
                
            case 3:
                System.out.print("Enter amount to add: ");
                double amount = scanner.nextDouble();
                scanner.nextLine();

                walletController.addMoneyViaPaymentMethod(
                    user.getUserId(),
                    amount
                );
                break;

            
            case 4:
                System.out.print("Receiver User ID: ");
                int receiverId = scanner.nextInt();

                System.out.print("Amount: ");
                double transferAmount = scanner.nextDouble();
                scanner.nextLine();

                System.out.print("Transaction PIN: ");
                String pin = scanner.nextLine();

                transactionController.transferMoney(
                        user.getUserId(), receiverId, transferAmount, pin
                );
                break;

            case 5:
                System.out.print("Request To User ID: ");
                int requestedUserId = scanner.nextInt();

                System.out.print("Amount: ");
                double requestAmount = scanner.nextDouble();
                scanner.nextLine();

                moneyRequestController.createRequest(
                        user.getUserId(),
                        requestedUserId,
                        requestAmount
                );
                break;
                
            case 6:
                transactionController.viewTransactions(user.getUserId());
                break;
                
            case 7:
                notificationController.viewNotifications(user.getUserId());
                break;
                
            case 8:
                moneyRequestsMenu(user);
                break;

            case 9:
                System.out.print("Current Password: ");
                String currentPassword = scanner.nextLine();

                System.out.print("New Password: ");
                String newPassword = scanner.nextLine();

                authController.changePassword(
                        user.getUserId(),
                        currentPassword,
                        newPassword
                );
                break;

            case 10:
                System.out.println("Logged out successfully");
                return;

            default:
                System.out.println("Invalid option");
            }
        }
    }
    
    private void moneyRequestsMenu(User user) {

        while (true) {
            System.out.println("\n--- Money Requests ---");
            System.out.println("1. View Incoming Requests");
            System.out.println("2. Accept Request");
            System.out.println("3. Decline Request");
            System.out.println("4. Back");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    moneyRequestController.viewIncomingRequests(user.getUserId());
                    break;

                case 2:
                    System.out.print("Request ID: ");
                    int acceptRequestId = scanner.nextInt();

                    System.out.print("Transaction PIN: ");
                    String pin = scanner.nextLine();

                    moneyRequestController.acceptRequest(
                            acceptRequestId,
                            user.getUserId(),
                            pin
                    );
                    break;

                case 3:
                	System.out.print("Request ID: ");
                    int declineRequestId = scanner.nextInt();
                    scanner.nextLine();

                    moneyRequestController.declineRequest(
                            declineRequestId
                    );
                    break;

                case 4:
                    return;

                default:
                    System.out.println("Invalid option");
            }
        }
    }
    
    
    /* ================= BUSINESS MENU ================= */
    
    private void businessMenu(User user) {

        while (true) {
        	
        	System.out.println("1. Wallet & Payments");
            System.out.println("2. Invoices");
            System.out.println("3. Loans");
            System.out.println("4. Analytics");
            System.out.println("5. Notifications");
            System.out.println("6. Security Settings");
            System.out.println("7. Logout");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
	            case 1:
	                walletAndPaymentsMenu(user);
	                break;
	            case 2:
	                invoicesMenu(user);
	                break;
	            case 3:
	                loansMenu(user);
	                break;
	            case 4:
	                analyticsMenu(user);
	                break;
	            case 5:
	                notificationController.viewNotifications(user.getUserId());
	                break;
	            case 6:
	                securitySettingsMenu(user);
	                break;
	            case 7:
	                System.out.println("Logged out successfully");
	                return;
	            default:
	                System.out.println("Invalid option");
            }
        }
    }
    
    
    

    
    
    /* ===== Business Sub-Menus ===== */
    
    private void walletAndPaymentsMenu(User user) {

        while (true) {
            System.out.println("\n--- Wallet & Payments ---");
            System.out.println("1. View Wallet");
            System.out.println("2. Add Business Payment Method");
            System.out.println("3. Accept Payment");
            System.out.println("4. Back");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    walletController.viewWallet(user.getUserId());
                    break;

                case 2:
                    System.out.print("Card Number: ");
                    String card = scanner.nextLine();

                    System.out.print("Card Holder Name: ");
                    String holder = scanner.nextLine();

                    System.out.print("Expiry Month (MM): ");
                    int month = scanner.nextInt();

                    System.out.print("Expiry Year (YYYY): ");
                    int year = scanner.nextInt();
                    scanner.nextLine();

                    paymentMethodController.addPaymentMethod(
                            user.getUserId(), card, holder, month, year, true);
                    break;

                case 3:
                    System.out.print("Invoice ID: ");
                    int invoiceId = scanner.nextInt();
                    scanner.nextLine();

                    invoiceController.payInvoice(invoiceId);
                    break;

                case 4:
                    return;

                default:
                    System.out.println("Invalid option");
            }
        }
    }

    
    private void invoicesMenu(User user) {

        while (true) {
            System.out.println("\n--- Invoices ---");
            System.out.println("1. Create Invoice");
            System.out.println("2. View Invoices");
            System.out.println("3. Pay Invoice");
            System.out.println("4. Send Invoice Notification");
            System.out.println("5. Back");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Customer User ID: ");
                    int customerId = scanner.nextInt();

                    System.out.print("Amount: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine();

                    System.out.print("Description: ");
                    String desc = scanner.nextLine();

                    invoiceController.createInvoice(
                            user.getUserId(), customerId, amount, desc);
                    break;

                case 2:
                    invoiceController.viewInvoices(user.getUserId());
                    break;

                case 3:
                    System.out.print("Invoice ID: ");
                    int invoiceId = scanner.nextInt();
                    scanner.nextLine();

                    invoiceController.payInvoice(invoiceId);
                    break;

                case 4:
                    System.out.print("Customer User ID: ");
                    int custId = scanner.nextInt();
                    scanner.nextLine();

                    notificationController.sendInvoiceReminder(
                            custId, user.getUserId());
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Invalid option");
            }
        }
    }

    
    private void loansMenu(User user) {

        while (true) {
            System.out.println("\n--- Loans ---");
            System.out.println("1. Apply for Loan");
            System.out.println("2. View Loan Status");
            System.out.println("3. Repay Loan");
            System.out.println("4. Back");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Loan Amount: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine();

                    System.out.print("Purpose: ");
                    String purpose = scanner.nextLine();

                    loanController.applyLoan(user.getUserId(), amount, purpose);
                    break;

                case 2:
                    loanController.viewLoans(user.getUserId());
                    break;

                case 3:
                    System.out.print("Loan ID: ");
                    int loanId = scanner.nextInt();
                    scanner.nextLine();

                    loanController.repayLoan(loanId);
                    break;

                case 4:
                    return;

                default:
                    System.out.println("Invalid option");
            }
        }
    }

    
    private void analyticsMenu(User user) {

        while (true) {
            System.out.println("\n--- Analytics ---");
            System.out.println("1. Business Analytics Dashboard");
            System.out.println("2. Top Customers");
            System.out.println("3. Back");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

            case 1:
                analyticsController.showBusinessAnalytics(user.getUserId());
                break;

            case 2:
                analyticsController.showTopCustomers(user.getUserId());
                break;

            case 3:
                return;
                
            default:
                System.out.println("Invalid option");
            }

        }
    }

    
    private void securitySettingsMenu(User user) {

        while (true) {
            System.out.println("\n--- Security Settings ---");
            System.out.println("1. Change Password");
            System.out.println("2. Change Transaction PIN");
            System.out.println("3. Back");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Current Password: ");
                    String current = scanner.nextLine();

                    System.out.print("New Password: ");
                    String newPass = scanner.nextLine();

                    authController.changePassword(
                            user.getUserId(), current, newPass);
                    break;

                case 2:
                	System.out.print("Current Transaction PIN: ");
                    String oldPin = scanner.nextLine();
                	
                    System.out.print("New Transaction PIN: ");
                    String newPin = scanner.nextLine();

                    authController.changeTransactionPin(
                            user.getUserId(), oldPin, newPin);
                    break;

                case 3:
                    return;

                default:
                    System.out.println("Invalid option");
            }
        }
    }

    
    /* ===== Utility / Helper ===== */
    
    private String getSecurityQuestion(int choice) {

        switch (choice) {
            case 1:
                return "What is your mother’s maiden name?";
            case 2:
                return "What was your first school?";
            case 3:
                return "What is your favorite color?";
            default:
                throw new IllegalArgumentException("Invalid security question choice");
        }
    }








}
