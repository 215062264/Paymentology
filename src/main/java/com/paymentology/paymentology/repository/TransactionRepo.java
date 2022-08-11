package com.paymentology.paymentology.repository;

import com.paymentology.paymentology.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {

}
