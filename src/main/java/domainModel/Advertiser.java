package domainModel;

import java.util.Objects;


public abstract class Advertiser {
    private final int id;
    private int bankAccount;


    public Advertiser(int id, int bankAccount) {
        this.id = id;
        this.bankAccount = bankAccount;
    }

    public int getId() {
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