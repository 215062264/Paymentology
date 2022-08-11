package com.paymentology.paymentology.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "transactionId")
    private String transactionID;
    @Column(name = "profileName ")
    private String profileName;
    @Column(name = "transactionDate")
    private String transactionDate;
    @Column(name = "transactionAmount")
    private float transactionAmount;
    @Column(name = "transactionNarrative")
    private String transactionNarrative;
    @Column(name = "transactionDescription")
    private String transactionDescription;
    @Column(name = "transactionType")
    private int transactionType;
    @Column(name = "walletReference")
    private String walletReference;

    public Transaction() {
    }

    public Transaction(long id, String transactionID, String profileName, String transactionDate, float transactionAmount, String transactionNarrative, String transactionDescription, int transactionType, String walletReference) {
        this.id = id;
        this.transactionID = transactionID;
        this.profileName = profileName;
        this.transactionDate = transactionDate;
        this.transactionAmount = transactionAmount;
        this.transactionNarrative = transactionNarrative;
        this.transactionDescription = transactionDescription;
        this.transactionType = transactionType;
        this.walletReference = walletReference;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
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

    public int getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(int transactionType) {
        this.transactionType = transactionType;
    }

    public String getWalletReference() {
        return walletReference;
    }

    public void setWalletReference(String walletReference) {
        this.walletReference = walletReference;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", transactionID=" + transactionID +
                ", profileName='" + profileName + '\'' +
                ", transactionDate='" + transactionDate + '\'' +
                ", transactionAmount=" + transactionAmount +
                ", transactionNarrative='" + transactionNarrative + '\'' +
                ", transactionDescription='" + transactionDescription + '\'' +
                ", transactionType=" + transactionType +
                ", walletReference='" + walletReference + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Transaction transaction = (Transaction) obj;
        return transactionID.equals(transaction.getTransactionID());
    }
    @Override
    public int hashCode() {
        return Objects.hash(transactionID);
    }

}
