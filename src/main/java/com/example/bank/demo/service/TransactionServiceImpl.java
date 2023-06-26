package com.example.bank.demo.service;


import com.example.bank.demo.dto.TransactionDTO;
import com.example.bank.demo.dto.TransactionRequest;
import com.example.bank.demo.entity.Account;
import com.example.bank.demo.entity.Transaction;
import com.example.bank.demo.mapper.TransactionMapper;
import com.example.bank.demo.repository.AccountRepository;
import com.example.bank.demo.repository.TransactionRepository;
import com.example.bank.demo.rest.TransactionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService{

    private TransactionRepository transactionRepository;
    private AccountRepository accountRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<TransactionDTO> getAllTransactions() {
        return transactionRepository.findAll()
                .stream()
                .map(t -> new TransactionDTO( t.getId(),
                        t.getSourceAccountId(),
                        t.getTargetAccountId(),
                        t.getCurrency()))
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDTO getTransactionById(UUID id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new TransactionNotFoundException());
        return TransactionMapper.INSTANCE.transactionToTransactionDTO(transaction);
    }

    @Override
    public TransactionDTO createTransaction(TransactionRequest transactionRequest)  {
        // Search for the source account defined on the request
        Optional<Account> optionalSourceAccount = accountRepository.findById(transactionRequest.sourceAccountId());
        Account sourceAccount = optionalSourceAccount.orElseThrow(() -> new IllegalArgumentException("Source account not found."));

        // Search for the target account defined on the request
        Optional<Account> optionalTargetAccount = accountRepository.findById(transactionRequest.targetAccountId());
        Account targetAccount = optionalTargetAccount.orElseThrow(() -> new IllegalArgumentException("Target account not found."));

        //Check if source and target account are the same
        if (targetAccount == sourceAccount) {
            throw new IllegalArgumentException("Source and target accounts cannot be the same.");
        }

        // Check if source account has sufficient balance for the amount defined on the request
        if (sourceAccount.getBalance().compareTo(transactionRequest.amount()) < 0) {
            throw new IllegalArgumentException("Insufficient balance in the source account.");
        }

        // Debit the transaction amount from the source account
        BigDecimal debitedAmount = transactionRequest.amount().negate(); // returns the value with the opposite sign , if the amount is 50, the debited amount is going to be -50
        sourceAccount.setBalance(sourceAccount.getBalance().add(debitedAmount)); //add the debited amount

        // Credit the transaction amount to the target account
        targetAccount.setBalance(targetAccount.getBalance().add(transactionRequest.amount())); //add the requested amount

        // Save the updated source account and target account
        accountRepository.save(sourceAccount);
        accountRepository.save(targetAccount);

        Transaction transaction = new Transaction(transactionRequest.sourceAccountId(), transactionRequest.targetAccountId(), transactionRequest.amount(), transactionRequest.currency());

        // Save the transaction
        Transaction savedTransaction = transactionRepository.save(transaction);

        return new TransactionDTO(savedTransaction.getId(), savedTransaction.getSourceAccountId(), savedTransaction.getTargetAccountId(), savedTransaction.getCurrency());
    }

    @Override
    public TransactionDTO updateTransaction(TransactionRequest transactionRequest) {
        // Check if the transaction exists, by the id
        Transaction existingTransaction = transactionRepository.findById(transactionRequest.id())
                .orElseThrow(() -> new TransactionNotFoundException());

        // Update the properties of the existing transaction
        existingTransaction.setAmount(transactionRequest.amount());
        existingTransaction.setCurrency(transactionRequest.currency());
        existingTransaction.setSourceAccountId(transactionRequest.sourceAccountId());
        existingTransaction.setTargetAccountId(transactionRequest.targetAccountId());

        // Save the updated transaction
        Transaction updatedTransaction = transactionRepository.save(existingTransaction);

        // Convert the updated transaction to a TransactionDTO
        TransactionDTO transactionDTO = new TransactionDTO(updatedTransaction.getId(),
                updatedTransaction.getSourceAccountId(), updatedTransaction.getTargetAccountId(), updatedTransaction.getCurrency());

        return transactionDTO;
    }


    @Override
    public void deleteTransaction(UUID id) {
        // Check if the transaction exists
        if (!transactionRepository.existsById(id)) {
            throw new TransactionNotFoundException();
        }

        // Delete the transaction by ID
        transactionRepository.deleteById(id);
    }
}
