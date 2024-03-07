package com.example.springjwtauthexample.web.model.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferMoneyRequest {
    @NotEmpty(message = "Recipient must not be empty")
    private String recipient;

    @NotNull(message = "Amount of money must not be null")
    @DecimalMin(value = "0.01", message = "Amount of money must be greater than or equal to 0.01")
    private BigDecimal amountMoney;
}