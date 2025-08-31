package com.example.payment.controller;//package com.example.payment.controller;

import com.example.payment.dto.response.ApiResponse;
import com.example.payment.dto.response.LookupResponse;
import com.example.payment.dto.response.PaymentCallbackResponse;
import com.example.payment.dto.response.PaymentInitiateResponse;
import com.example.payment.service.KhaltiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final KhaltiService khaltiPaymentService;

    @PostMapping("/initiate")
    public ResponseEntity<ApiResponse<PaymentInitiateResponse>> initiatePayment(@RequestParam String orderId,
                                                                                @RequestParam String orderName,
                                                                                @RequestParam int amount) {
        PaymentInitiateResponse response = khaltiPaymentService.initiatePayment(orderId, orderName, amount);
        return ResponseEntity.ok(ApiResponse.success("Payment initiated successfully", response));
    }

    @GetMapping("/callback")
    public ResponseEntity<ApiResponse<PaymentCallbackResponse>> paymentCallback(PaymentCallbackResponse paymentCallbackResponse) {
        // Optional: verify payment status using lookup API
        LookupResponse lookup = khaltiPaymentService.lookupPayment(paymentCallbackResponse.getPidx());

        if (!"Completed".equalsIgnoreCase(lookup.getStatus())) {
            return ResponseEntity.ok(
                    ApiResponse.error("Payment not completed: " + lookup.getStatus(), paymentCallbackResponse)
            );
        }

        // Return success response
        return ResponseEntity.ok(
                ApiResponse.success("Payment completed successfully", paymentCallbackResponse)
        );
    }


    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<LookupResponse>> verifyPayment(@RequestParam String pidx) {
        LookupResponse response = khaltiPaymentService.lookupPayment(pidx);
        return ResponseEntity.ok(ApiResponse.success("Payment verification successful", response));
    }
}
