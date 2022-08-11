package com.paymentology.paymentology.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.MappingStrategy;
import com.paymentology.paymentology.entity.Transaction;
import com.paymentology.paymentology.csvPojo.TransactionCsvPojo;
import com.paymentology.paymentology.repository.TransactionRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class TransactionService {

    @Autowired
    TransactionRepo repo;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public List convertCSVToList(MultipartFile file) {

        List<TransactionCsvPojo> transactionList = null;

        //read csv file and store the result in a list
        try {
            MappingStrategy<TransactionCsvPojo> mappingStrategy = new HeaderColumnNameMappingStrategy<>();
            mappingStrategy.setType(TransactionCsvPojo.class);
            Reader reader = new InputStreamReader(file.getInputStream(), "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(reader) {
                public String readLine() throws IOException {
                    String line = super.readLine();
                    return line.endsWith(",") ? line.substring(0, line.length() - 1) : line;
                }
            };

            //Create a CSV Bean reader object
            CsvToBean<TransactionCsvPojo> csvToBean = new CsvToBeanBuilder<TransactionCsvPojo>(bufferedReader)
                    .withMappingStrategy(mappingStrategy)
                    .build();

            //convert the csvToBean object to a list of transactions
            transactionList = csvToBean.parse();

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return transactionList;
     }

     public ArrayList<Transaction> convertCsvPojoToDataEntity(List<TransactionCsvPojo> list){

         ArrayList transactionList = new ArrayList<>();

         for (TransactionCsvPojo pojo: list) {
             Transaction transaction = new Transaction();
             transaction.setId(pojo.getId());
             transaction.setTransactionID(pojo.getTransactionID());
             transaction.setProfileName(pojo.getProfileName());
             transaction.setTransactionDate(pojo.getTransactionDate());
             transaction.setTransactionAmount(pojo.getTransactionAmount());
             transaction.setTransactionNarrative(pojo.getTransactionNarrative());
             transaction.setTransactionDescription(pojo.getTransactionDescription());
             transaction.setTransactionType(pojo.getTransactionType());
             transaction.setWalletReference(pojo.getWalletReference());
             transactionList.add(transaction);
         }
        return transactionList;
     }


    public ArrayList<Transaction> removeDuplicates(List<Transaction> list) {

        //get's rid of all duplicates in the list
        List<Transaction> uniqueRecords = list
                       .stream()
                       .distinct()
                       .collect(Collectors.toList());

        ArrayList<Transaction> modifiedList = new ArrayList<>(uniqueRecords);

        //find the reversal records and add them to the list
        for (Transaction t: list) {
            if(t.getTransactionDescription().equals("REVERSAL")){
                modifiedList.add(t);
            }
        }

        return modifiedList;
    }

     //save individual record to the database
     public Transaction save(Transaction transaction){
        return repo.saveAndFlush(transaction);
     }

    //save list of records to the database
     public Iterable<Transaction> saveAll(Iterable<Transaction> list){
        return repo.saveAllAndFlush(list);
     }

     //fetch list of transactions from database
     public Iterable<Transaction> getListOfTransactions(){
        return repo.findAll();
     }

     public List<Transaction> sortList(List list){
        if(!list.isEmpty()){
            Collections.sort(list, Comparator.comparing(Transaction::getTransactionID));
        }
        return list;
     }

     public int checkMatchedIDs(ArrayList<Transaction> list1, ArrayList<Transaction> list2){

        int matchedId = 0;

         Iterator<Transaction> l1 = list1.iterator();
         Iterator<Transaction> l2 = list2.iterator();

         while (l1.hasNext() && l2.hasNext()){
             Transaction t1 = l1.next();
             Transaction t2 = l2.next();
             if(t1.getTransactionID().equals(t2.getTransactionID())){
                 matchedId++;
             }
         }
        return matchedId;
     }

    public ArrayList<Transaction> unmatchedRecords(ArrayList<Transaction> list1, ArrayList<Transaction> list2){

        Iterator<Transaction> i1 = list1.iterator();
        Iterator<Transaction> i2 = list2.iterator();
        ArrayList<Transaction> unmatched = new ArrayList();

        while (i1.hasNext() && i2.hasNext()){
            Transaction t1 = i1.next();
            Transaction t2 = i2.next();
            if(!t1.getTransactionID().equals(t2.getTransactionID())){
                unmatched.add(t1);
                unmatched.add(t2);
            }
        }

        return unmatched;
    }

}
