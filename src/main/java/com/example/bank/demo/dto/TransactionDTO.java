package com.example.bank.demo.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionDTO(
        UUID id,
        int sourceAccountId,

        int targetAccountId,

        String currency
) {

}
