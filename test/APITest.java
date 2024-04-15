package test;
import static org.junit.Assert.*;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import atm.*;

import java.io.FileWriter;

public class APITest {
    @Before
    public void start(){
        JSONObject database = new JSONObject();

        try  {
            FileWriter file = new FileWriter("./src/atm/database.json");

            database.put("utilityAccount", null);
            database.put("savingsAccount", null);
            database.put("checkingAccount", null);
            database.put("dayNum", 0);
            database.put("bills", null);
            file.write(database.toJSONString());
            file.flush();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

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
        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.setBalance(1000);
        API.setCheckingAccount(checkingAccount);

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
        UtilityAccount utilAccount = new UtilityAccount("test", "pass");
        checkingAccount.setBalance(1000);
        savingsAccount.setBalance(500);
        API.setCheckingAccount(checkingAccount);
        API.setSavingsAccount(savingsAccount);
        API.setDayNum(1);

        assertFalse(utilAccount.checkPassword("wrongPass"));
        assertFalse(utilAccount.checkUsername("wrongName"));
        assertTrue(utilAccount.checkUsername("test"));
        assertTrue(utilAccount.checkPassword("pass"));

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

        UtilityAccount utilityAccount = new UtilityAccount("CS317", "password");

        API.setUtilityAccount(utilityAccount);
        API.setCheckingAccount(checkingAccount);
        API.setSavingsAccount(savingsAccount);

        assertNotNull(API.getCheckingAccount()); // Checking account stored
        assertNotNull(API.getSavingsAccount()); // Savings account stored
        assertNotNull(API.getUtilityAccount()); // Utility account stored
    }
}
