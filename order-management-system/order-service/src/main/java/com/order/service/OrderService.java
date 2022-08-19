package com.order.service;

import akka.actor.typed.ActorSystem;
import com.order.actor.OrderEventSourceActor;
import com.order.actor.OrderStatus;
import com.order.actor.OrderStatusActor;
import com.order.client.FulfillmentClient;
import com.order.client.PaymentProviderClient;
import com.order.command.*;
import com.order.dto.DeliverStatusResponseDTO;
import com.order.dto.OrderRequestDTO;
import com.order.dto.PaymentDTO;
import io.micronaut.scheduling.annotation.Async;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Singleton
@Slf4j
public class OrderService {
    private final PaymentProviderClient paymentProviderClient;
    private final FulfillmentClient fulfillmentClient;
    private ActorSystem<Command> mainActor;

    public OrderService(PaymentProviderClient paymentProviderClient, FulfillmentClient fulfillmentClient) {
        this.paymentProviderClient = paymentProviderClient;
        this.fulfillmentClient = fulfillmentClient;
    }

    public void createOrder(OrderRequestDTO orderRequestDTO) {
        log.info("Creating order!");
        String orderId = UUID.randomUUID().toString();
        mainActor = ActorSystem.create(OrderEventSourceActor.create(orderId,
                paymentProviderClient, fulfillmentClient), "MainActor");

        ActorSystem<OrderStatus> orderStatusActor =
                ActorSystem.create(OrderStatusActor.create(), "OrderStatusActor");

        mainActor.tell(creatOrderCMD(orderRequestDTO, orderId, orderStatusActor));

        log.info("Order is being processed!");
    }

    private CreateOrder creatOrderCMD(OrderRequestDTO dto, String orderId,
                                      ActorSystem<OrderStatus> orderStatusActor) {
        return CreateOrder.builder()
                .orderId(orderId)
                .userId(dto.getUserId())
                .basketId(dto.getBasketId())
                .amount(dto.getTotalAmount())
                .replyTo(orderStatusActor)
                .build();
    }

    @Async
    public void updateOrderStatus(PaymentDTO paymentDTO) {
        log.info("Updating order payment status!");

        mainActor = ActorSystem.create(OrderEventSourceActor.create(paymentDTO.getOrderId(),
                paymentProviderClient, fulfillmentClient), "MainActor");

        ActorSystem<OrderStatus> orderStatusActor =
                ActorSystem.create(OrderStatusActor.create(), "OrderStatusActor");

        mainActor.tell(new OrderPayment(paymentDTO.getOrderId(), paymentDTO.getUserId(),
                paymentDTO.getTotalAmount(), orderStatusActor));

        log.info("Order amount paid!");
    }

    @Async
    public void updateOrderDeliveryStatus(DeliverStatusResponseDTO deliverStatusResponseDTO) {
        log.info("Updating order delivery status!");

        mainActor = ActorSystem.create(OrderEventSourceActor.create(deliverStatusResponseDTO.getOrderId(),
                paymentProviderClient, fulfillmentClient), "MainActor");

        ActorSystem<OrderStatus> orderStatusActor =
                ActorSystem.create(OrderStatusActor.create(), "OrderStatusActor");

        mainActor.tell(new FulfillOrder(deliverStatusResponseDTO.getOrderId(), orderStatusActor));

//        mainActor.tell(new CloseOrder(deliverStatusResponseDTO.getOrderId(), orderStatusActor));

        log.info("Order delivered!");
    }
}
