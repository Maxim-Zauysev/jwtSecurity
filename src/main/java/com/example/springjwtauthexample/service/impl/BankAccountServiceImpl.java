package com.example.springjwtauthexample.service.impl;

import com.example.springjwtauthexample.entity.BankAccount;
import com.example.springjwtauthexample.exception.TransactionalException;
import com.example.springjwtauthexample.repository.BankAccountRepository;
import com.example.springjwtauthexample.service.BankAccountService;
import com.example.springjwtauthexample.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {

    private final UserService userService;
    private final BankAccountRepository bankAccountRepository;

    @Override
    @Transactional
    public void transferMoney(String sender, String recipient, BigDecimal amount) {
        BankAccount senderAccount  = bankAccountRepository.findByUserUsername(sender);
        BankAccount recipientAccount  = bankAccountRepository.findByUserUsername(recipient);

        synchronized (senderAccount ) {
            synchronized (recipientAccount ) {
                if (senderAccount.getBalance().compareTo(amount) < 0) {
                    throw new TransactionalException("Insufficient funds");
                }
                senderAccount.setBalance(senderAccount.getBalance().subtract(amount));
                recipientAccount.setBalance(recipientAccount.getBalance().add(amount));

                bankAccountRepository.save(senderAccount);
                bankAccountRepository.save(recipientAccount);
                log.info("Transfer of {} from {} to {} was successful.", amount, sender, recipient);

            }
        }

    }



}
