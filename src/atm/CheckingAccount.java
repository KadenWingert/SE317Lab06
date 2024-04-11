package atm;
public class CheckingAccount {
    private long amountWithdrawn;
    private long amountDeposited;
    private long balance;

    
    public long getAmountWithdrawn() {
        return this.amountWithdrawn;
    }

    public long getAmountDepo() {
        return this.amountDeposited;
    }

    public long getBalance() {
        return this.balance;
    }
    
    public void setAmountWithdrawn(long amount) {
        this.amountWithdrawn = amount;
    }

    public void setAmountDepo(long amount) {
        this.amountDeposited = amount;
    }
    public void setBalance(long balance) {
        this.balance = balance;
    }
}
