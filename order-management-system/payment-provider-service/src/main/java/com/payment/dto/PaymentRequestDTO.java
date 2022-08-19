package com.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class PaymentRequestDTO implements Serializable {
    private String orderId;
    private String userId;
    private double totalAmount;
}
