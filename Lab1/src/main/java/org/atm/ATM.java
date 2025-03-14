package org.atm;

import java.util.ArrayList;
import java.util.List;

public class ATM {
    private List<Account> accounts;

    private static int accNumber = 0;
    private Account currAcc;

    private boolean loggedIn;

    public ATM() {
        accounts = new ArrayList<Account>();
    }

    public boolean GetState() {
        return loggedIn;
    }

    public void Withdraw(int amount) throws InsufficientFundsException, InvalidAmountException, AuthenticationException {
        if (currAcc == null) {
            throw new AuthenticationException("Authentication error");
        }
        currAcc.Withdraw(amount);
    }

    public void Replenish(int amount) throws InvalidAmountException, AuthenticationException {
        if (currAcc == null) {
            throw new AuthenticationException("Authentication error");
        }
        currAcc.Replenish(amount);
    }

    public void TransactionHistory() throws AuthenticationException {
        if (currAcc == null) {
            throw new AuthenticationException("Authentication error");
        }
        List<String> history = currAcc.getTransactionHistory();

        for (String op: history) {
            System.out.println(op);
        }
    }

    public int NewNumber() {
        accNumber++;
        return accNumber;
    }

    public int CreateAccount(int pin) {
        int newAccNum = NewNumber();
        Account newAcc = new Account(newAccNum, pin);
        accounts.add(newAcc);

        return newAccNum;
    }


    public void LogIn(int accNum, int pin)  throws AuthenticationException {
        Account acc = getAccount(accNum);

        if (acc == null) {
            throw new AuthenticationException("Account not found: " + accNum);
        }
        if (!acc.CheckPin(pin)) {
            throw new AuthenticationException("Wrong pin");
        }

        loggedIn = true;
        currAcc = acc;
    }

    public void LogOut() throws AuthenticationException {
        if (currAcc == null) {
            throw new AuthenticationException("Error");
        }

        loggedIn = false;
        currAcc = null;
    }

    private Account getAccount(int accNum){
        for (Account acc: accounts) {
            if (acc.getAccountNumber() == accNum) {
                return acc;
            }
        }
        return null;
    }
}
