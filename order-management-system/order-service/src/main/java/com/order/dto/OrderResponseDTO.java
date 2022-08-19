package com.order.dto;

import lombok.Data;

@Data
public class OrderResponseDTO {
    private final int status;
    private final String message;
}
