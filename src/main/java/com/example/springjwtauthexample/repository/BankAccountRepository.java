package com.example.springjwtauthexample.repository;

import com.example.springjwtauthexample.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount,Long> {
    BankAccount findByUserUsername(String username);
}
