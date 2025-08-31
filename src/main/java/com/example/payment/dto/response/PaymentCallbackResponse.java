package com.example.payment.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCallbackResponse{
    private String pidx;
    private String txnId;
    private Integer amount;
    private String status;
    private String mobile;
    private String purchase_order_id;
    private String purchase_order_name;
}