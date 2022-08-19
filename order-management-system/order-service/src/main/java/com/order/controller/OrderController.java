package com.order.controller;

import com.order.dto.DeliverStatusResponseDTO;
import com.order.dto.OrderRequestDTO;
import com.order.dto.OrderResponseDTO;
import com.order.dto.PaymentDTO;
import com.order.service.OrderService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.validation.Validated;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Validated
@Controller("/api/v1/orders")
@Slf4j
public class OrderController {

    @Inject
    private OrderService orderService;

    @Get
    public HttpStatus status() {
        return HttpStatus.OK;
    }

    @Post
    public HttpResponse<OrderResponseDTO> createProduct(OrderRequestDTO orderRequestDTO) {

        orderService.createOrder(orderRequestDTO);

        return HttpResponse.status(HttpStatus.CREATED).
                body(new OrderResponseDTO(HttpStatus.CREATED.getCode(), "Order Created!"));
    }

    //TODO: Callback url should be in RequestParam
    @Post("/callback/payment")
    public HttpStatus paymentCallback(PaymentDTO paymentDTO) {
        log.info("In Payment endpoint!");
        orderService.updateOrderStatus(paymentDTO);
        return HttpStatus.OK;
    }

    //TODO: Callback url should be in RequestParam
    @Post("/callback/status")
    public HttpStatus fulfillmentStatus(DeliverStatusResponseDTO deliverStatusResponseDTO) {
        log.info("In Fulfillment endpoint");
        log.info("In Payment endpoint!");
        orderService.updateOrderDeliveryStatus(deliverStatusResponseDTO);

        return HttpStatus.OK;
    }

}
