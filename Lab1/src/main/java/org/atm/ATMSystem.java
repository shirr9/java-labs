package org.atm;

import java.util.Scanner;

/**
 * Главный класс системы банкомата, представляющий консольный интерфейс для взаимодействия с банкоматом.
 * Если еще нет авторизованного пользователя, то доступны только опции созания аккаутна и входа в аккаунт.
 * Если пользователь авторизован, то может выполнить следующие операции над своим счетом:
 * 1. снятие денег со счета
 * 2. пополнение счета
 * 3. просмотр истории операций
 * 4. просмотр баланса счета
 */
public class ATMSystem {
    public static void main(String[] args) {
        ATM atm = new ATM();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nATM System Menu:");
            if (atm.IsLoggedIn()) {
                System.out.println("1. Withdraw");
                System.out.println("2. Replenish");
                System.out.println("3. Transaction History");
                System.out.println("4. Balance");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();

                try {
                    switch (choice) {
                        case 1:
                            System.out.print("Enter amount to withdraw: ");
                            int withdrawAmount = scanner.nextInt();
                            atm.Withdraw(withdrawAmount);
                            System.out.println("Withdrawal successful!");
                            break;
                        case 2:
                            System.out.print("Enter amount to replenish: ");
                            int replenishAmount = scanner.nextInt();
                            atm.Replenish(replenishAmount);
                            System.out.println("Replenishment successful!");
                            break;
                        case 3:
                            atm.TransactionHistory();
                            break;
                        case 4:
                            int balance = atm.GetBalance();
                            System.out.println("Balance: " + balance);
                            break;
                        case 5:
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

            } else {
                System.out.println("1. Create Account");
                System.out.println("2. Log In");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();

                try {
                    switch (choice) {
                        case 1:
                            System.out.print("Enter new PIN: ");
                            int newPin = scanner.nextInt();
                            int accountNumber = atm.CreateAccount(newPin);
                            System.out.println("Account created successfully!" + "Account number: " + accountNumber);
                            break;
                        case 2:
                            System.out.print("Enter account number: ");
                            int accNumber = scanner.nextInt();
                            System.out.print("Enter PIN: ");
                            int pin = scanner.nextInt();
                            atm.LogIn(accNumber, pin);
                            System.out.println("Login successful!");
                            break;
                        case 3:
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
}
