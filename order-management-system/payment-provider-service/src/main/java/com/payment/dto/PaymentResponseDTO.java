package com.payment.dto;

import lombok.Data;

@Data
public class PaymentResponseDTO {
    private final String orderId;
    private final String userId;
    private final String paymentId;
}
