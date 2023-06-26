package com.example.bank.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity //map to the database table
@Table(name="transaction")
public class Transaction {


    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    @Column(name="id")
    private @Getter @Setter UUID id;

    @Column(name="source_account_id")
    private @Getter @Setter int sourceAccountId;

    @Column(name="target_account_id")
    private @Getter @Setter int targetAccountId;
    @Column(name="amount")
    private @Getter @Setter BigDecimal amount;

    @Column(name="currency")
    private @Getter @Setter String currency;

    public Transaction(int sourceAccountId, int targetAccountId, BigDecimal amount, String currency) {
        this.sourceAccountId = sourceAccountId;
        this.currency = currency;
        this.amount = amount;
        this.targetAccountId = targetAccountId;
    }
}
