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
@Table(name="account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private @Getter @Setter int id;
    @Column(name="balance")
    private @Getter @Setter BigDecimal balance;
    @Column(name="currency")
    private @Getter @Setter String currency;
    @Column(name="created_at")
    private @Getter @Setter Date createdAt;

    public Account(BigDecimal balance, String currency, Date createdAt) {
        this.balance = balance;
        this.currency = currency;
        this.createdAt = createdAt;
    }
}
