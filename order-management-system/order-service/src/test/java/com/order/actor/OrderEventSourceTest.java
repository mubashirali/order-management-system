package com.order.actor;

import akka.actor.testkit.typed.javadsl.TestKitJunitResource;
import akka.actor.testkit.typed.javadsl.TestProbe;
import akka.actor.typed.ActorRef;
import akka.pattern.StatusReply;
import com.order.client.FulfillmentClient;
import com.order.client.PaymentProviderClient;
import com.order.command.Command;
import com.order.command.CreateOrder;
import com.order.command.OrderPayment;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mock;

import java.util.UUID;

public class OrderEventSourceTest {

    @Mock
    private PaymentProviderClient paymentProviderClient;
    @Mock
    private FulfillmentClient fulfillmentClient;


    @ClassRule
    public static final TestKitJunitResource testKit = new TestKitJunitResource(
            "akka.persistence.journal.plugin = \"akka.persistence.journal.inmem\" \n" +
                    "akka.persistence.snapshot-store.plugin = \"akka.persistence.snapshot-store.local\"  \n" +
                    "akka.persistence.snapshot-store.local.dir = \"target/snapshot-" + UUID.randomUUID().toString() + "\"  \n"
    );


    @Test
    public void shouldCreateOrder() {
        String orderId = UUID.randomUUID().toString();
        String basketId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();

        ActorRef<Command> cart = testKit.spawn(OrderEventSourceActor
                .create(orderId, paymentProviderClient, fulfillmentClient));
        TestProbe<OrderStatus> probe = testKit.createTestProbe();
        cart.tell(getBuild(orderId, basketId, userId, probe));

        OrderStatus reply = probe.receiveMessage();
        Assert.assertNotNull(reply);
        Assert.assertEquals(OrderStatus.CREATED, reply);

        testKit.stop(cart);

        cart = testKit.spawn(OrderEventSourceActor
                .create(orderId, paymentProviderClient, fulfillmentClient));
        probe = testKit.createTestProbe();
        cart.tell(new OrderPayment(orderId, 123.2f, probe.getRef()));

        reply = probe.receiveMessage();
        Assert.assertNotNull(reply);
        Assert.assertEquals(OrderStatus.PAID, reply);
    }

    private CreateOrder getBuild(String orderId, String basketId, String userId,
                                 TestProbe<OrderStatus> probe) {
        return CreateOrder.builder()
                .orderId(orderId)
                .userId(userId)
                .basketId(basketId)
                .amount(123.01)
                .replyTo(probe.getRef())
                .build();
    }

}