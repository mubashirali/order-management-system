package com.order.actor;


import akka.actor.typed.Behavior;
import akka.actor.typed.SupervisorStrategy;
import akka.persistence.typed.PersistenceId;
import akka.persistence.typed.javadsl.*;
import com.order.client.FulfillmentClient;
import com.order.client.PaymentProviderClient;
import com.order.command.*;
import com.order.dto.DeliverStatusResponseDTO;
import com.order.dto.OrderStateDTO;
import com.order.dto.PaymentDTO;
import com.order.event.*;
import com.order.state.OrderState;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

import static com.order.actor.OrderStatus.*;

@Slf4j
public class OrderEventSourceActor extends EventSourcedBehavior<Command, Event, OrderState> {
    private final PaymentProviderClient paymentProviderClient;
    private final FulfillmentClient fulfillmentClient;
    private final String orderId;

    private OrderEventSourceActor(String orderId, PaymentProviderClient paymentProviderClient,
                                  FulfillmentClient fulfillmentClient) {
        super(PersistenceId.of("Order", orderId));
        /*TODO*/
        SupervisorStrategy.restartWithBackoff(Duration.ofMillis(200), Duration.ofSeconds(5), 0.1);

        this.paymentProviderClient = paymentProviderClient;
        this.fulfillmentClient = fulfillmentClient;
        this.orderId = orderId;
    }

    public static Behavior<Command> create(String orderId, PaymentProviderClient paymentProviderClient,
                                           FulfillmentClient fulfillmentClient) {
        return new OrderEventSourceActor(orderId, paymentProviderClient, fulfillmentClient);
    }

    @Override
    public OrderState emptyState() {
        return new OrderState();
    }

    @Override
    public CommandHandler<Command, Event, OrderState> commandHandler() {
        StateChangeCmdHandler handler = new StateChangeCmdHandler();
        return newCommandHandlerBuilder().forAnyState()
                .onCommand(CreateOrder.class, handler::onCreateOrder)
                .onCommand(OrderPayment.class, handler::onOrderPayment)
                .onCommand(FulfillOrder.class, handler::onFulFullOrder)
                .onCommand(CloseOrder.class, handler::onOrderClose)
                .build();
    }

    @Override
    public EventHandler<OrderState, Event> eventHandler() {
        return newEventHandlerBuilder()
                .forAnyState()
                .onEvent(OrderCreated.class, this::getOrderState)
                .onEvent(OrderPaid.class, (state, event) -> state.updateStatus(event.getStatus()))
                .onEvent(Fulfillment.class, (state, event) -> state.updateStatus(event.getStatus()))
                .onEvent(Closed.class, (state, event) -> state.updateStatus(event.getStatus())).build();
    }

    @Override
    public RetentionCriteria retentionCriteria() {
        return RetentionCriteria.snapshotEvery(100, 3);
    }

    private class StateChangeCmdHandler {
        Effect<Event, OrderState> onCreateOrder(OrderState state, CreateOrder cmd) {
            //TODO: Should be event
            PaymentDTO paymentDTO = new PaymentDTO(cmd.getOrderId(), cmd.getUserId(), cmd.getAmount());
            paymentProviderClient.requestPayment(paymentDTO);

            //TODO: Persist in DB
            return Effect().persist(new OrderCreated(cmd.getOrderId(), cmd.getUserId(), cmd.getBasketId(),
                            cmd.getAmount(), CreateOrder.getStatus()))
                    .thenRun(updatedCart -> cmd.replyTo.tell(CREATED));
        }

        private Effect<Event, OrderState> onOrderPayment(OrderState state, OrderPayment cmd) {
            log.info("Paid!!!!!!!!!!!!!!!!!");

            //TODO: Should be event
            fulfillmentClient.requestDelivery(new DeliverStatusResponseDTO(cmd.getOrderId(),
                    cmd.getUserId()));

            //TODO: Persist in DB
            return Effect().persist(new OrderPaid(cmd.getOrderId(), OrderPayment.getStatus()))
                    .thenRun(updatedCart -> cmd.replyTo.tell(PAID));

        }

        private Effect<Event, OrderState> onFulFullOrder(OrderState state, FulfillOrder cmd) {
            log.info("Delivered!!!!!!!!!!!!!!!!!");

            //TODO: Persist in DB
            return Effect().persist(new Fulfillment(cmd.getOrderId(), FulfillOrder.getStatus()))
                    .thenRun(updatedCart -> cmd.replyTo.tell(FULFILLMENT));

        }

        private Effect<Event, OrderState> onOrderClose(OrderState state, CloseOrder cmd) {
            //TODO: Persist in DB
            return Effect().persist(new Closed(cmd.getOrderId(), CloseOrder.getStatus()))
                    .thenRun(updatedCart -> cmd.replyTo.tell(CLOSED));

        }
    }

    private OrderState getOrderState(OrderState state, OrderCreated event) {
        OrderStateDTO stateDTO =
                OrderStateDTO.builder()
                        .orderId(event.getOrderId())
                        .userId(event.getUserId())
                        .basketId(event.getBasketId())
                        .totalAmount(event.getTotalAmount()).status(event.getStatus())
                        .build();
        return state.create(stateDTO);
    }
}
