package com.example.payment;

import lombok.Data;

@Data
public class TransactionStatus {
    private String productCode;
    private String transactionUuid;
    private Double totalAmount;
    private String status;
    private String refId;
}