package atm;
public class SavingsAccount {
    private long amountDeposited;

    private long balance;
    private long amountTransferred;
    private long amountWithdrawn;

    public long getAmountDepo() {
        return this.amountDeposited;
    }

    public long getBalance() {
        return this.balance;
    }
    public long getAmountTransferred() {
        return this.amountTransferred;
    }
    
    public long getAmountWithdrawn() {
        return this.amountWithdrawn;
    }

    public void setAmountDepo(long amount) {
        this.amountDeposited = amount;
    }

    public void setBalance(long bal) {
        this.balance = bal;
    }
    public void setAmountTransferred(long amount) {
        this.amountTransferred = amount;
    }
    
    public void setAmountWithdrawn(long amount) {
        this.amountWithdrawn = amount;
    }


}
