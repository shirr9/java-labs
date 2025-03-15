import org.atm.AuthenticationException;
import org.atm.InsufficientFundsException;
import org.atm.InvalidAmountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.atm.ATM;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class AtmTests {
    private ATM atm;

    @BeforeEach
    void setUp() {
        atm = new ATM();
    }

    @Test
    void testWithdrawWithoutLogin() {
        Exception exception = assertThrows(AuthenticationException.class, () -> {
            atm.Withdraw(100);
        });
    }

    @Test
    void testWithdrawWithInsufficientFunds() throws AuthenticationException {
        int accountNumber = atm.CreateAccount(52);
        atm.LogIn(accountNumber, 52);
        Exception exception = assertThrows(InsufficientFundsException.class, () -> {
            atm.Withdraw(100);
        });
    }

    @Test
    void testWithdrawWithNegativeAmount() throws AuthenticationException {
        int accountNumber = atm.CreateAccount(52);
        atm.LogIn(accountNumber, 52);
        Exception exception = assertThrows(InvalidAmountException.class, () -> {
            atm.Withdraw(-50);
        });
    }

    @Test
    void testReplenishWithNegativeAmount() throws AuthenticationException {
        int accountNumber = atm.CreateAccount(52);
        atm.LogIn(accountNumber, 52);
        Exception exception = assertThrows(InvalidAmountException.class, () -> {
            atm.Replenish(-50);
        });
    }

    @Test
    void testReplenishWithoutLogin() {
        Exception exception = assertThrows(AuthenticationException.class, () -> {
            atm.Replenish(100);
        });
    }

    @Test
    void testLogInWithWrongPin() {
        int accountNumber = atm.CreateAccount(52);
        Exception exception = assertThrows(AuthenticationException.class, () -> {
            atm.LogIn(accountNumber, 5252);
        });
    }

    @Test
    void testLogInWithNoExistentAccount() {
        Exception exception = assertThrows(AuthenticationException.class, () -> {
            atm.LogIn(1, 5252);
        });
    }

    @Test
    void testLogoutWithoutLogin() {
        Exception exception = assertThrows(AuthenticationException.class, () -> {
            atm.LogOut();
        });
    }

    @Test
    void testGetBalanceWithoutLogin() {
        Exception exception = assertThrows(AuthenticationException.class, () -> {
            atm.GetBalance();
        });
    }

    @Test
    void testTransactionHistoryWithoutLogin() {
        Exception exception = assertThrows(AuthenticationException.class, () -> {
            atm.TransactionHistory();
        });
    }
}
