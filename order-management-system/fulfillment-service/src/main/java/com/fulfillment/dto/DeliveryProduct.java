package com.fulfillment.dto;


import akka.actor.typed.ActorRef;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeliveryProduct {
    public final String orderId;
    public final String userId;
    public final ActorRef<DeliverStatusResponseDTO> replyTo;
}
