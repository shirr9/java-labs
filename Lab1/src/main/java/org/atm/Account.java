package org.atm;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс - банковский аккаунт пользователя.
 */
public class Account {
    private final int accountNumber;
    private final int pin;
    private int balance;
    private final List<String> transactionHistory;

    /**
     * Создаёт новый аккаунт с указанным номером и PIN-кодом.
     * @param accountNumber номер аккаунта
     * @param pin PIN-код для входа
     */
    public Account(int accountNumber, int pin) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = 0;
        this.transactionHistory = new ArrayList<>();
        transactionHistory.add("Account created");
    }

    /**
     * Аутентифицирует пользователя по PIN-коду.
     * @param pin введённый PIN-код
     * @return true, если PIN правильный, иначе false
     */
    public boolean Authenticate(int pin) {
        return pin == this.pin;
    }

    /**
     * Возвращает номер аккаунта.
     * @return номер аккаунта
     */
    public int getAccountNumber() {
        return accountNumber;
    }

    /**
     * Возвращает баланс на счете.
     * @return текущий баланс.
     */
    public int getBalance() {
        return balance;
    }

    /**
     * Снимает деньги со счета.
     * @param amount сумма снятия.
     * @throws InsufficientFundsException в случае недостатка для снятия денег.
     * @throws InvalidAmountException если сумма снятия некорректна.
     */
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

    /**
     * Пополняет счет.
     * @param amount сумма пополнения.
     * @throws InvalidAmountException если сумма пополнения некорректна.
     */
    public void Replenish(int amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Replenish amount must be positive");
        }
        balance += amount;
        transactionHistory.add("Replenish: " + amount);
    }

    /**
     * Возвращает историю транзакций.
     * @return список строк с транзакциями.
     */
    public List<String> getTransactionHistory() {
        return transactionHistory;
    }
}
