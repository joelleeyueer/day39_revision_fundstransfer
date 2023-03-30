package ibf2022.batch2.paf.serverstub.models;

public class BankAccount {
    private Integer accountId;
    private String accountName;
    private Float balance;

    public BankAccount(Integer accountId, String accountName, Float balance) {
        this.accountId = accountId;
        this.accountName = accountName;
        this.balance = balance;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }
}
