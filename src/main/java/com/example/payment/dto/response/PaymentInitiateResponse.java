package com.example.payment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInitiateResponse {
    private String pidx;
    private String payment_url;
    private String expires_at;
    private Integer expires_in;
}