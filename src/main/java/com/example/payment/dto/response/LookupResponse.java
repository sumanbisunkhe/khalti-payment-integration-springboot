package com.example.payment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LookupResponse {
    private String pidx;
    private Integer total_amount;
    private String status;
    private String transaction_id;
    private Integer fee;
    private boolean refunded;
}