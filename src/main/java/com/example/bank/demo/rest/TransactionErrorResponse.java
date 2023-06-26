package com.example.bank.demo.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class TransactionErrorResponse {

    private @Getter
    @Setter int status;
    private @Getter @Setter String message;
    private @Getter @Setter long timeStamp;

    private @Getter @Setter List<String> errors;
    private @Getter @Setter String errorDetails;

    public TransactionErrorResponse(String message, List<String> errors) {
        this.message = message;
        this.errors = errors;
    }

    public TransactionErrorResponse(String message, String errorDetails) {
        this.message = message;
        this.errorDetails = errorDetails;
    }
}
