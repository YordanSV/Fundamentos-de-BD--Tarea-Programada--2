/**
 * Account
 */
public class Account {
    String accountNumber;
    String identity;
    String amount;
    
    public Account(String accountNumber, String identity, String amount){
        this.accountNumber = accountNumber;
        this.identity = identity;
        this.amount = amount;
    }

    public String getAccountNumber(){
        return accountNumber;
    }
}