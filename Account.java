/**
 * Account
 */
public class Account {
    String accountNumber;
    String identity;
    String amount;

    public Account(String accountNumber, String identity, String amount) {
        this.accountNumber = accountNumber;
        this.identity = identity;
        this.amount = amount;
    }

    public String getIdentity() {
        return identity;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String modify) {
        amount = Integer.toString(Integer.parseInt(amount) + Integer.parseInt(modify));
    }
}