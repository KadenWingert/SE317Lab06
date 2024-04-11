package atm;
public class Bill {
    private long payDate;
    private long payAmount;
    private boolean payStatus;

    public long getPayDate()  {
        return this.payDate;
    }

    public long getPayAmount()  {
        return this.payAmount;
    }

    public boolean getPayStatus()  {
        return this.payStatus;
    }

    public void setPayDate(long date) {
        this.payDate = date; 
    }

    public void setPayAmount(long amount) {
        this.payAmount = amount; 
    }

    public void setPayStatus(boolean status) {
        this.payStatus = status; 
    }
}
