package com.example.bank.demo.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionResponse(
        UUID id,
        int sourceAccountId,

        int targetAccountId,

        String currency
) {
}
