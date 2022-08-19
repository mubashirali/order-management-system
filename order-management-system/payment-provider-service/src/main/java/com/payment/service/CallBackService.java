package com.payment.service;

import com.payment.client.OrderClient;
import com.payment.dto.PaymentRequestDTO;
import com.payment.dto.PaymentResponseDTO;
import io.micronaut.http.HttpStatus;
import io.micronaut.scheduling.annotation.Async;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@Singleton
public class CallBackService {

    private final OrderClient client;

    public CallBackService(OrderClient client) {
        this.client = client;
    }

    @Async
    public void processPayment(PaymentRequestDTO request) {
        log.info("Putting on sleep!");

        pauseMainThread();

        /*TODO: Uses Kafka type of thing not the REST API
         *   Retry queue
         */
        client.paymentCallback(new PaymentResponseDTO(request.getOrderId(), request.getUserId(),
                UUID.randomUUID().toString()));

        log.info("Request Processed: {}", request.getOrderId());
    }

    private void pauseMainThread() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            log.error("Wait timeout {}", e.getMessage());
        }
    }
}
