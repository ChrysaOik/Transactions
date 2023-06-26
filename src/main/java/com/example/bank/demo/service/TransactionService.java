package com.example.bank.demo.service;

import com.example.bank.demo.dto.TransactionDTO;
import com.example.bank.demo.dto.TransactionRequest;
import com.example.bank.demo.entity.Transaction;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;
import java.util.UUID;

public interface TransactionService {

    List<TransactionDTO> getAllTransactions();

    TransactionDTO getTransactionById(UUID id);

    TransactionDTO createTransaction(TransactionRequest transactionRequest) throws ChangeSetPersister.NotFoundException;

    TransactionDTO updateTransaction(TransactionRequest transactionRequest);

    void deleteTransaction(UUID id);
}
