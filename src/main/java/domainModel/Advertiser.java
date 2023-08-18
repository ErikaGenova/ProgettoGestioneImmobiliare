package domainModel;

import java.util.Objects;


public abstract class Advertiser {
    private final String id;
    private int bankAccount;


    public Advertiser(String id, int bankAccount) {
        this.id = id;
        this.bankAccount = bankAccount;
    }

    public String getId() {
        return id;
    }

    public int getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(int bankAccount) {
        this.bankAccount = bankAccount;
    }

    public abstract void displayInformation(); //TODO: da verificare se serve
}