package com.order.client;

import com.order.dto.DeliverStatusResponseDTO;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;

import static io.micronaut.http.HttpHeaders.CONTENT_TYPE;

@Client(id = "fulfillment")
public interface FulfillmentClient {

    @Post("/api/v1/fulfillments")
    @Header(name = CONTENT_TYPE, value = "application/json")
    HttpResponse<Integer> requestDelivery(@Body DeliverStatusResponseDTO deliverStatusResponseDTO);
}
