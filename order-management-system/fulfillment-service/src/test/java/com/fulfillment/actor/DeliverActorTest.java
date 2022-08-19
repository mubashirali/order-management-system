package com.fulfillment.actor;

import akka.actor.testkit.typed.javadsl.TestKitJunitResource;
import akka.actor.testkit.typed.javadsl.TestProbe;
import akka.actor.typed.ActorRef;
import com.fulfillment.dto.DeliverStatusResponseDTO;
import com.fulfillment.dto.DeliveryProduct;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.UUID;

public class DeliverActorTest {

    @ClassRule
    public static final TestKitJunitResource testKit = new TestKitJunitResource();

    @Test
    public void shouldResponseStatus() {
        //Given
        String orderId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();

        ActorRef<DeliveryProduct> spawn = testKit.spawn(DeliverActor.create());
        TestProbe<DeliverStatusResponseDTO> probe = testKit.createTestProbe();

        //When
        spawn.tell(new DeliveryProduct(orderId, userId, probe.getRef()));

        //Then
        DeliverStatusResponseDTO deliverStatusResponseDTO = probe.receiveMessage();
        Assert.assertNotNull(deliverStatusResponseDTO);
        Assert.assertEquals(orderId, deliverStatusResponseDTO.getOrderId());
        Assert.assertEquals(userId, deliverStatusResponseDTO.getUserId());
    }

}