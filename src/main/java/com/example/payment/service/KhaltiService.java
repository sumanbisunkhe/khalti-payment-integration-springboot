package com.example.payment.service;

import com.example.payment.dto.request.CustomerInfo;
import com.example.payment.dto.request.LookupRequest;
import com.example.payment.dto.request.PaymentInitiateRequest;
import com.example.payment.dto.response.LookupResponse;
import com.example.payment.dto.response.PaymentInitiateResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class KhaltiService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${khalti.base.url}")
    private String baseUrl;

    @Value("${khalti.secret.key}")
    private String secretKey;

    @Value("${khalti.return.url}")
    private String returnUrl;

    @Value("${khalti.website.url}")
    private String websiteUrl;

    // Initiate Payment
    public PaymentInitiateResponse initiatePayment(String orderId, String orderName, int amount) {
        PaymentInitiateRequest request = new PaymentInitiateRequest();
        request.setReturn_url(returnUrl);
        request.setWebsite_url(websiteUrl);
        request.setAmount(amount);
        request.setPurchase_order_id(orderId);
        request.setPurchase_order_name(orderName);
        request.setCustomer_info(new CustomerInfo("Suman Bisunkhe", "sumanbisunkhe304@gmail.com", "9840948275"));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", secretKey);

        HttpEntity<PaymentInitiateRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<PaymentInitiateResponse> response = restTemplate.exchange(
                baseUrl + "/epayment/initiate/",
                HttpMethod.POST,
                entity,
                PaymentInitiateResponse.class
        );

        return response.getBody();
    }

    // Lookup Payment
    public LookupResponse lookupPayment(String pidx) {
        LookupRequest request = new LookupRequest(pidx);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", secretKey);

        HttpEntity<LookupRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<LookupResponse> response = restTemplate.exchange(
                baseUrl + "/epayment/lookup/",
                HttpMethod.POST,
                entity,
                LookupResponse.class
        );

        return response.getBody();
    }
}
