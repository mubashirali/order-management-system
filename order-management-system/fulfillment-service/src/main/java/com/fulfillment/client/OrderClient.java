package com.fulfillment.client;

import com.fulfillment.dto.DeliverStatusResponseDTO;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;

import static io.micronaut.http.HttpHeaders.CONTENT_TYPE;

@Client(id ="order")
public interface OrderClient {

    @Post("/api/v1/orders/callback/status")
    @Header(name = CONTENT_TYPE, value = "application/json")
    HttpStatus deliveryStatus(@Body DeliverStatusResponseDTO responseDTO);
}
