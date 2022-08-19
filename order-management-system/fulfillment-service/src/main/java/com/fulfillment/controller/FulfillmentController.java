package com.fulfillment.controller;

import com.fulfillment.dto.FulfillmentRequestDTO;
import com.fulfillment.service.ProcessOrderDelivery;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.validation.Validated;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@Controller("/api/v1/fulfillments")
public class FulfillmentController {

    @Inject
    private ProcessOrderDelivery process;


    //TODO: Callback url should be in RequestParam
    @Post
    public HttpResponse<Integer> requestDelivery(FulfillmentRequestDTO request) {
        log.info("Processing Delivery {}", request);

        process.deliver(request);

        return HttpResponse.status(HttpStatus.OK);
    }
}
