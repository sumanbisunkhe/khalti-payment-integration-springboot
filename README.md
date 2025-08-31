# Khalti Payment Integration
<img src="https://upload.wikimedia.org/wikipedia/commons/e/ee/Khalti_Digital_Wallet_Logo.png.jpg" alt="Khalti Logo" width="200"/>

A professional Spring Boot application for integrating Khalti payment gateway services into your Java applications.

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java Version](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## 📋 Table of Contents

- [Features](#-features)
- [Prerequisites](#-prerequisites)
- [Installation](#-installation)
- [Configuration](#-configuration)
- [Usage](#-usage)
- [API Documentation](#-api-documentation)
- [Security](#-security)
- [Testing](#-testing)
- [License](#-license)
- [Contributing](#-contributing)

## ✨ Features

- **Payment Initiation**: Easily initiate Khalti payments with customizable parameters
- **Payment Verification**: Verify payment status and transaction details using Khalti's lookup API
- **Callback Handling**: Process payment callbacks from Khalti
- **Error Handling**: Comprehensive error handling for payment processing
- **Configurable Settings**: Easily configure merchant details and environment settings

## 🔧 Prerequisites

- Java 17 or higher
- Maven 3.6.3 or higher
- Spring Boot 3.5.5
- Khalti Merchant Account

## 📥 Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/khalti-payment-integration.git
   cd khalti-payment-integration
   ```

2. Build the project:
   ```bash
   ./mvnw clean install
   ```

3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

## ⚙️ Configuration

Configure your Khalti merchant details in `application.properties`:

```properties
# Khalti configuration
khalti.base.url=https://dev.khalti.com/api/v2
khalti.secret.key=Key 05bf95cc57244045b8df5fad06748dab
khalti.return.url=http://localhost:8080/payment/callback
khalti.website.url=http://localhost:8080
```

For production, update the URLs and credentials accordingly.

## 🚀 Usage

### Initiating a Payment

```java
@PostMapping("/initiate")
public ResponseEntity<ApiResponse<PaymentInitiateResponse>> initiatePayment(@RequestParam String orderId,
                                                                            @RequestParam String orderName,
                                                                            @RequestParam int amount) {
   PaymentInitiateResponse response = khaltiPaymentService.initiatePayment(orderId, orderName, amount);
   return ResponseEntity.ok(ApiResponse.success("Payment initiated successfully", response));
}
```

### Processing Payment Callback

```java
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
```

### Verifying Payment Status

```java
@PostMapping("/verify")
public ResponseEntity<ApiResponse<LookupResponse>> verifyPayment(@RequestParam String pidx) {
   LookupResponse response = khaltiPaymentService.lookupPayment(pidx);
   return ResponseEntity.ok(ApiResponse.success("Payment verification successful", response));
}
```

## 📚 API Documentation

### Key Endpoints

- `POST /payment/initiate` - Initiate a new payment
- `GET /payment/callback` - Handle payment callback from Khalti
- `POST /payment/verify` - Verify payment status using Khalti's lookup API

### Request/Response Models

#### Payment Initiation Request
```java
public class PaymentInitiateRequest {
   private String return_url;
   private String website_url;
   private Integer amount; // in paisa
   private String purchase_order_id;
   private String purchase_order_name;
   private CustomerInfo customer_info;
}
```

#### Payment Initiation Response
```java
public class PaymentInitiateResponse {
   private String pidx;
   private String payment_url;
   private String expires_at;
   private Integer expires_in;
}
```

#### Payment Callback Response
```java
public class PaymentCallbackResponse {
   private String pidx;
   private String txnId;
   private Integer amount;
   private String status;
   private String mobile;
   private String purchase_order_id;
   private String purchase_order_name;
}
```

## 🔒 Security

This implementation includes:

- Proper authentication with Khalti API using secret key
- Secure handling of merchant credentials
- Verification of payment status using Khalti's lookup API

## 🧪 Testing

For testing purposes, use the Khalti sandbox environment:

```properties
khalti.base.url=https://dev.khalti.com/api/v2
```

## 📄 License

This project is licensed under the  [MIT License](LICENSE).



## 👥 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

<p align="center">
  Made with ❤️ By <a href="https://github.com/sumanbisunkhe">Suman Bisunkhe</a>
</p>
