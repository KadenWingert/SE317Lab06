package atm;
public class User {
    public UtilityAccount utilityAccount;
    public SavingsAccount savingsAccount;
    public CheckingAccount checkingAccount;

    public User(){
        this.utilityAccount = API.getUtilityAccount();

        this.savingsAccount = API.getSavingsAccount();

        this.checkingAccount = API.getCheckingAccount();
    }
}
