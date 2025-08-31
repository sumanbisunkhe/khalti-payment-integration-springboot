package com.example.payment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInitiateRequest {
    private String return_url;
    private String website_url;
    private Integer amount; // in paisa
    private String purchase_order_id;
    private String purchase_order_name;
    private CustomerInfo customer_info;
}