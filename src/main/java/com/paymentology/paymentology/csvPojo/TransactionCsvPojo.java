package com.paymentology.paymentology.csvPojo;

import com.opencsv.bean.CsvBindByName;

public class TransactionCsvPojo {

    /*
    * @CsvBindByName - This annotation is provided by OpenCSV to specify a binding
    * between a column name of the CSV input and a field in a bean
    * */
    @CsvBindByName
    private long id;
    @CsvBindByName(column = "TransactionID")
    private String transactionID;
    @CsvBindByName(column = "ProfileName")
    private String profileName;
    @CsvBindByName(column = "TransactionDate")
    private String transactionDate;
    @CsvBindByName(column = "TransactionAmount")
    private float transactionAmount;
    @CsvBindByName(column = "TransactionNarrative")
    private String transactionNarrative;
    @CsvBindByName(column = "TransactionDescription")
    private String transactionDescription;
    @CsvBindByName(column = "TransactionType")
    private int transactionType;
    @CsvBindByName(column = "WalletReference")
    private String walletReference;

    public TransactionCsvPojo() {
    }

    public TransactionCsvPojo(long id, String profileName, String transactionDate, float transactionAmount, String transactionNarrative, String transactionDescription, String transactionID, int transactionType, String walletReference) {
        this.id = id;
        this.profileName = profileName;
        this.transactionDate = transactionDate;
        this.transactionAmount = transactionAmount;
        this.transactionNarrative = transactionNarrative;
        this.transactionDescription = transactionDescription;
        this.transactionID = transactionID;
        this.transactionType = transactionType;
        this.walletReference = walletReference;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public float getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(float transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionNarrative() {
        return transactionNarrative;
    }

    public void setTransactionNarrative(String transactionNarrative) {
        this.transactionNarrative = transactionNarrative;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public int getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(int transactionType) { this.transactionType = transactionType; }

    public String getWalletReference() {
        return walletReference;
    }

    public void setWalletReference(String walletReference) {
        this.walletReference = walletReference;
    }
}
