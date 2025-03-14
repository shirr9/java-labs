package org.atm;

import java.util.Scanner;

public class ATMSystem {
    public static void main(String[] args) {
        ATM atm = new ATM();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nATM System Menu:");
            if (atm.GetState()) {
                System.out.println("3. Withdraw");
                System.out.println("4. Replenish");
                System.out.println("5. Transaction History");
                System.out.println("6. Exit");
                System.out.print("Choose an option: ");
            } else {
                System.out.println("1. Create Account");
                System.out.println("2. Log In");
                System.out.print("Choose an option: ");
            }

            int choice = scanner.nextInt();

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter new PIN: ");
                        int newPin = scanner.nextInt();
                        int accountNum = atm.CreateAccount(newPin);
                        System.out.println("Account created successfully!" + "Account number: " + accountNum);
                        break;
                    case 2:
                        System.out.print("Enter account number: ");
                        int accNum = scanner.nextInt();
                        System.out.print("Enter PIN: ");
                        int pin = scanner.nextInt();
                        atm.LogIn(accNum, pin);
                        System.out.println("Login successful!");
                        break;
                    case 3:
                        System.out.print("Enter amount to withdraw: ");
                        int withdrawAmount = scanner.nextInt();
                        atm.Withdraw(withdrawAmount);
                        System.out.println("Withdrawal successful!");
                        break;
                    case 4:
                        System.out.print("Enter amount to replenish: ");
                        int replenishAmount = scanner.nextInt();
                        atm.Replenish(replenishAmount);
                        System.out.println("Replenishment successful!");
                        break;
                    case 5:
                        atm.TransactionHistory();
                        break;
                    case 6:
                        atm.LogOut();
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
