package com.example.springjwtauthexample.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(mappedBy = "bankAccount")
    private User user;
    private BigDecimal balance;
    private BigDecimal initialDeposit;
}