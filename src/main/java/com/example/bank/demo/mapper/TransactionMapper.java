package com.example.bank.demo.mapper;
import com.example.bank.demo.dto.TransactionDTO;
import com.example.bank.demo.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    TransactionDTO transactionToTransactionDTO(Transaction transaction);
}
