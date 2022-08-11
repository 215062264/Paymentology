package com.paymentology.paymentology.repository;

import com.paymentology.paymentology.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM TRANSACTION WHERE id IN (SELECT MIN(id) FROM TRANSACTION GROUP BY TRANSACTION_ID)")
    ArrayList<Transaction> getUnmatchedRecords();
}
