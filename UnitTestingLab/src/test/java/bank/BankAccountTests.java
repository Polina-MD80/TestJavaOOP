package bank;




import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.rules.ExpectedException;

public
class BankAccountTests {
    @Test
    public
    void testBankAccountCreationStartsWithZeroValue () {
        BankAccount bankAccount = new BankAccount ();
        double      balance     = bankAccount.getBalance ();
        assertEquals (0.0, balance, 0);
    }

    @Test
    public
    void testDepositShouldIncreaseAmountCorrectly () {
        BankAccount bankAccount = new BankAccount ();
        bankAccount.deposit (300);
        assertEquals (300, bankAccount.getBalance (), 0);
    }
    @Test(expected = IllegalStateException.class)
    public
    void testDepositShouldNotAcceptNegativeDeposits () {
        BankAccount bankAccount = new BankAccount ();
        bankAccount.deposit (-300);
        assertEquals (0, bankAccount.getBalance (), 0);
    }
}
