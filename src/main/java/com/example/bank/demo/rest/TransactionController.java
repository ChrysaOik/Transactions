package com.example.bank.demo.rest;

import com.example.bank.demo.dto.TransactionDTO;
import com.example.bank.demo.dto.TransactionRequest;
import com.example.bank.demo.dto.TransactionResponse;
import com.example.bank.demo.entity.Transaction;
import com.example.bank.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("")
    public ResponseEntity<List<TransactionResponse>> getAllTransactions() {
        List<TransactionDTO> transactionDTOs= transactionService.getAllTransactions();
        List<TransactionResponse> transactionResponses = transactionDTOs.stream()
                .map(t -> new TransactionResponse(t.id(), t.sourceAccountId(), t.targetAccountId(), t.currency()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(transactionResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable UUID id) {
        TransactionDTO transaction = transactionService.getTransactionById(id);
        TransactionResponse transactionResponse = new TransactionResponse(transaction.id(), transaction.sourceAccountId(), transaction.targetAccountId(), transaction.currency());

        return ResponseEntity.ok(transactionResponse);
    }

    @PostMapping("")
   public ResponseEntity<TransactionResponse> createTransaction(@RequestBody TransactionRequest transactionRequest) throws ChangeSetPersister.NotFoundException {
        TransactionDTO transactionDTO = transactionService.createTransaction(transactionRequest);
        TransactionResponse transactionResponse = new TransactionResponse(transactionDTO.id(), transactionDTO.sourceAccountId(), transactionDTO.targetAccountId(), transactionDTO.currency());
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionResponse); //201 created
    }

    @PutMapping("")
    public ResponseEntity<TransactionResponse> updateTransaction(@RequestBody TransactionRequest transactionRequest) {
        TransactionDTO transactionDTO= transactionService.updateTransaction(transactionRequest);
        TransactionResponse transactionResponse = new TransactionResponse(transactionDTO.id(), transactionDTO.sourceAccountId(), transactionDTO.targetAccountId(), transactionDTO.currency());
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionResponse);
    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable UUID id) {
        transactionService.deleteTransaction(id);
    }
}
