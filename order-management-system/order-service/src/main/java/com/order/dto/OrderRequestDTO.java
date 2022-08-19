package com.order.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;


@Getter
@RequiredArgsConstructor
public class OrderRequestDTO {

    @NotNull
    private String userId;

    @NotNull
    private String basketId;

    private double totalAmount;

    @NotEmpty
    private List<BasketProduct> products;
}
