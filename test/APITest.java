package test;
import static org.junit.Assert.*;

import org.junit.Test;

import atm.*;

public class APITest {

    @Test
    public void testNullStorage() {
        assertNull(API.getCheckingAccount());
        assertNull(API.getSavingsAccount());
        assertEquals(0, API.getDayNum());
        assertNull(API.getUtilityAccount());
    }

    @Test
    public void testNullElementWithMultipleElements() {
        CheckingAccount checkingAccount = new CheckingAccount();
        SavingsAccount savingsAccount = new SavingsAccount();
        checkingAccount.setBalance(1000);
        savingsAccount.setBalance(500);
        API.setCheckingAccount(checkingAccount);
        API.setSavingsAccount(savingsAccount);

        assertNull(API.getUtilityAccount()); // No utility account stored
        assertNotNull(API.getCheckingAccount()); // Checking account stored
        assertNotNull(API.getSavingsAccount()); // Savings account stored
    }

    @Test
    public void testNullSingleElement() {
        assertNull(API.getUtilityAccount()); // No utility account stored
    }

    @Test
    public void testIncompatibleTypes() {
        assertNull(API.getUtilityAccount()); // No utility account stored
        assertNotNull(API.getCheckingAccount()); // Checking account stored
        assertNull(API.getSavingsAccount()); // Savings account not stored
    }

    @Test
    public void testEmptyElements() {
        assertNull(API.getUtilityAccount()); // No utility account stored
        assertNull(API.getCheckingAccount()); // No checking account stored
        assertNull(API.getSavingsAccount()); // No savings account stored
    }

    @Test
    public void testNormalCases() {
        CheckingAccount checkingAccount = new CheckingAccount();
        SavingsAccount savingsAccount = new SavingsAccount();
        checkingAccount.setBalance(1000);
        savingsAccount.setBalance(500);
        API.setCheckingAccount(checkingAccount);
        API.setSavingsAccount(savingsAccount);
        API.setDayNum(1);

        assertNotNull(API.getCheckingAccount()); // Checking account stored
        assertNotNull(API.getSavingsAccount()); // Savings account stored
        assertEquals(1, API.getDayNum()); // Day number is 1
    }

    @Test
    public void testMultipleElements() {
        CheckingAccount checkingAccount = new CheckingAccount();
        SavingsAccount savingsAccount = new SavingsAccount();
        checkingAccount.setBalance(1000);
        savingsAccount.setBalance(500);
       //TODO
        // UtilityAccount utilityAccount = new UtilityAccount();
        API.setCheckingAccount(checkingAccount);
        API.setSavingsAccount(savingsAccount);

        assertNotNull(API.getCheckingAccount()); // Checking account stored
        assertNotNull(API.getSavingsAccount()); // Savings account stored
        assertNotNull(API.getUtilityAccount()); // Utility account stored
    }
}
