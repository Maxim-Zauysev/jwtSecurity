package com.example.springjwtauthexample.service;

import java.math.BigDecimal;

public interface BankAccountService {
    void transferMoney(String sender, String recipient, BigDecimal amount);
}
