package org.atm;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private final int accountNumber;
    private final int pin;
    private int balance;
    private List<String> transactionHistory;

    public Account(int accountNumber, int pin) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = 0;
        this.transactionHistory = new ArrayList<>();
        transactionHistory.add("Account created");
    }


    public boolean CheckPin(int pin) {
        return pin == this.pin;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public int getPin() {
        return pin;
    }

    public int getBalance() {
        return balance;
    }

    public void Withdraw(int amount) throws InsufficientFundsException, InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive");
        }
        if (amount > balance) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        balance -= amount;
        transactionHistory.add("Withdraw: " + amount);
    }

    public void Replenish(int amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Replenish amount must be positive");
        }
        balance += amount;
        transactionHistory.add("Replenish: " + amount);
    }

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }
}
