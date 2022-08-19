package com.fulfillment.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FulfillmentRequestDTO {
    private String orderId;
    private String userId;
}
