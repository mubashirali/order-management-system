package com.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class PaymentDTO implements Serializable {
    private String orderId;
    private String userId;
    private double totalAmount;
}
