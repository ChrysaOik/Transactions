package com.example.bank.demo.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionRequest(
        UUID id,
        int sourceAccountId,
        int targetAccountId,
        BigDecimal amount,
        String currency
) {
}
