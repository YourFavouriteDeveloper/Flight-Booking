package org.example.models;

import org.example.enums.AccountType;
import org.example.enums.Gender;

public class Accounts {
    private String username;
    private String password;
    private AccountType accountType;
    private Passengers passenger;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = AccountType.valueOf(accountType.substring(0, 1).toUpperCase() + accountType.substring(1).toLowerCase());
    }

    public Passengers getPassenger() {
        return passenger;
    }

    public void setPassenger(Passengers passenger) {
        this.passenger = passenger;
    }

    public Accounts(String username, String password, String accountType,Passengers passenger) {
        this.username = username;
        this.password = password;
        this.accountType = AccountType.valueOf(accountType.substring(0, 1).toUpperCase() + accountType.substring(1).toLowerCase());
        this.passenger = passenger;
    }

    public Accounts(String accountType) {
        this.accountType = AccountType.valueOf(accountType.substring(0, 1).toUpperCase() + accountType.substring(1).toLowerCase());
    }
}
