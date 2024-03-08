package com.example.springjwtauthexample.event;
import com.example.springjwtauthexample.entity.BankAccount;
import com.example.springjwtauthexample.repository.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
@Service
@ConditionalOnProperty(
        name = "app.bank.balance.update",
        havingValue = "true"
)
@RequiredArgsConstructor
public class BalanceUpdater {

    private final BankAccountRepository bankAccountRepository;
    private static final Logger logger = LoggerFactory.getLogger("com.example.transactionalz");

    @Transactional
    @Scheduled(fixedRate = 10000)
    public void increaseBalance() {
        List<BankAccount> accounts = bankAccountRepository.findAll();
        for (BankAccount account : accounts) {
            BigDecimal balance = account.getBalance();
            BigDecimal newBalance = balance.multiply(new BigDecimal("1.05"));
            BigDecimal maxBalance = balance.multiply(new BigDecimal("2.07"));
            if (newBalance.compareTo(maxBalance) > 0) {
                account.setBalance(maxBalance);
            } else {
                account.setBalance(newBalance);
            }
            bankAccountRepository.save(account);
            log.info("Balance of account {} was updated from {} to {}.", account.getId(), balance, account.getBalance());
        }
    }
}
