package com.paymentology.paymentology.controller;

import com.paymentology.paymentology.entity.Transaction;
import com.paymentology.paymentology.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;


@Controller
public class UploadController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TransactionService service;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @PostMapping("/upload-csv-file")
    public String uploadCSVFile(@RequestParam("file1") MultipartFile file,
                                @RequestParam("file2") MultipartFile file2,
                                Model model){
        // check if a file has been uploaded, if not, display error message
        if (file.isEmpty() || file2.isEmpty()) {
            model.addAttribute("message", "Please make sure to select 2 CSV files to upload.");
            model.addAttribute("status", false);
        }else {
            try {
                //fetch data and convert csv files to list objects
                List transactionList1 = service.convertCSVToList(file);
                List transactionList2 = service.convertCSVToList(file2);

                //convert csv pojo to data entity
                ArrayList<Transaction> tList1 = service.convertCsvPojoToDataEntity(transactionList1);
                ArrayList<Transaction> tList2 = service.convertCsvPojoToDataEntity(transactionList2);

                //sort both lists by TransactionID
                service.sortList(tList1);
                service.sortList(tList2);

                //check and compare records that match using Transaction_ID field
                int matched = service.checkMatchedIDs(tList1, tList2);
                int unmatched1 = tList1.size() - matched;
                int unmatched2 = tList2.size() - matched;

                //remove duplicates from each list
                ArrayList<Transaction> noDuplicate1 = service.removeDuplicates(tList1);
                ArrayList<Transaction> noDuplicate2 = service.removeDuplicates(tList2);

                //need to get a list of unmatched records
                ArrayList<Transaction> unmatchedList = service.unmatchedRecords(noDuplicate1, noDuplicate2);
                System.out.println("noDuplicate1 Size: " + noDuplicate1.size());
                System.out.println("noDuplicate2 Size: " + noDuplicate2.size());
                System.out.println("Unmatched Size: " + unmatchedList.size());

                int totalUnmatched = unmatched1 + unmatched2;
                int numOfDuplicates = totalUnmatched - unmatchedList.size();

                //store the lists to the database
                service.saveAll(tList1);
                service.saveAll(tList2);

                //save to model
                model.addAttribute("totalRecords1", transactionList1.size());
                model.addAttribute("matchedIds", matched);
                model.addAttribute("unmatched1", unmatched1);
                model.addAttribute("totalRecords2", transactionList2.size());
                model.addAttribute("unmatched2", unmatched2);
                model.addAttribute("status", true);
                model.addAttribute("unmatchedRecords", unmatchedList);
                model.addAttribute("unmatchedListSize", unmatchedList.size());
                model.addAttribute("numOfDuplicates", numOfDuplicates);

            }catch (Exception e) {
                model.addAttribute("message", "An error occurred while processing the CSV file.");
                model.addAttribute("status", false);
                logger.info(e.getMessage());
            }
        }
        return "result-page";
    }

}
