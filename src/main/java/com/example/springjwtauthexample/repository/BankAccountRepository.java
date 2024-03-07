package com.example.springjwtauthexample.repository;

import com.example.springjwtauthexample.entity.BankAccount;
import com.example.springjwtauthexample.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,Long> {
}
