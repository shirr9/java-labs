package org.atm;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс - банкомат. Производит операции над банковскими аккаунтами пользователей.
 */
public class ATM {
    private final List<Account> accounts;

    private static int accountsNumber = 0;
    private Account currAccount;

    private boolean isLoggedIn;

    /**
     * Создает новый банкомат.
     */
    public ATM() {
        accounts = new ArrayList<Account>();
    }

    /**
     * Проверяет, есть ли в банкомате авторизованный пользователь.
     * @return true, если пользователь авторизован, иначе - false.
     */
    public boolean IsLoggedIn() {
        return isLoggedIn;
    }

    /**
     * Снимает деньги со счета текущего авторизованного пользователя.
     * @param amount сумма снятия.
     * @throws InsufficientFundsException в случае недостатка для снятия денег.
     * @throws InvalidAmountException если сумма снития некорректна.
     * @throws AuthenticationException если в банкомате нет авторизованного пользователя, но кто то проводит операцию снятия.
     */
    public void Withdraw(int amount) throws InsufficientFundsException, InvalidAmountException, AuthenticationException {
        if (currAccount == null) {
            throw new AuthenticationException("Authentication error");
        }
        currAccount.Withdraw(amount);
    }

    /**
     * Пополняет счет текущего авторизованного пользователя.
     * @param amount сумма пополнения.
     * @throws InvalidAmountException если сумма пополнения некорректна.
     * @throws AuthenticationException если в банкомате нет авторизованного пользователя, но кто то проводит операцию пополнения.
     */
    public void Replenish(int amount) throws InvalidAmountException, AuthenticationException {
        if (currAccount == null) {
            throw new AuthenticationException("Authentication error");
        }
        currAccount.Replenish(amount);
    }

    /**
     * Выводит историю транзакций для текущего авторизованного пользователя.
     * @throws AuthenticationException если в банкомате нет авторизованного пользователя.
     */
    public void TransactionHistory() throws AuthenticationException {
        if (currAccount == null) {
            throw new AuthenticationException("Authentication error");
        }
        List<String> history = currAccount.getTransactionHistory();

        for (String transaction: history) {
            System.out.println(transaction);
        }
    }

    /**
     * Вычисляет номер для создания следующего аккаунта путем инкремента статической переменной.
     * @return номер следующего для создания аккаунта.
     */
    public int NewNumber() {
        accountsNumber++;
        return accountsNumber;
    }

    /**
     * Создает новый аккаунт. Аккаунт добавляется в общий список аккаунтов банкомата.
     * @param pin PIN-код аккаунта.
     * @return номер только что созданного счета.
     */
    public int CreateAccount(int pin) {
        int newAccNum = NewNumber();
        Account newAcc = new Account(newAccNum, pin);
        accounts.add(newAcc);

        return newAccNum;
    }

    /**
     * Возвращает баланс счета для текущего авторизованного пользователя.
     * @return баланс счета.
     * @throws AuthenticationException если в банкомате нет авторизованного пользователя.
     */
    public int GetBalance() throws AuthenticationException {
        if (currAccount == null) {
            throw new AuthenticationException("Authentication error");
        }
        return currAccount.getBalance();
    }

    /**
     * Выполняет вход в аккаунт.
     * @param accNum номер счета.
     * @param pin PIN-код аккаунта.
     * @throws AuthenticationException если был введен неправильный PIN-код аккаунта.
     */
    public void LogIn(int accNum, int pin)  throws AuthenticationException {
        Account account = getAccount(accNum);

        if (account == null) {
            throw new AuthenticationException("Account not found: " + accNum);
        }
        if (!account.Authenticate(pin)) {
            throw new AuthenticationException("Wrong pin");
        }

        isLoggedIn = true;
        currAccount = account;
    }

    /**
     * Выполняет выход пользователя из аккаунта.
     * @throws AuthenticationException если есть попытка выхода из аккаунта, при условии что не было входа в аккаунт.
     */
    public void LogOut() throws AuthenticationException {
        if (currAccount == null) {
            throw new AuthenticationException("No account logged in");
        }

        isLoggedIn = false;
        currAccount = null;
    }

    /**
     * Ищет аккаунт по номер аккаунта.
     * @param accNum номер аккаунта.
     * @return аккаунт.
     */
    private Account getAccount(int accNum){
        for (Account account: accounts) {
            if (account.getAccountNumber() == accNum) {
                return account;
            }
        }
        return null;
    }
}
