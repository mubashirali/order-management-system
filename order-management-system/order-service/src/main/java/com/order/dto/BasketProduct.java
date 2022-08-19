package com.order.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@RequiredArgsConstructor
public class BasketProduct {
    @NotNull
    private String productId;
    @NotNull
    private int quantity;
}
