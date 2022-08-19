package com.fulfillment.dto;

import com.fulfillment.status.DeliverStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DeliverStatusResponseDTO {
    private final String orderId;
    private final String userId;
    private final DeliverStatus status;
}
