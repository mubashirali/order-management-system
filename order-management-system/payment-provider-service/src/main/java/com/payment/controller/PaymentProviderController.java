package com.payment.controller;

import com.payment.dto.PaymentRequestDTO;
import com.payment.service.CallBackService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.validation.Validated;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@Controller("/api/v1/payments")
public class PaymentProviderController {

    @Inject
    private CallBackService callBackService;

    //TODO: Callback url should be in RequestParam
    @Post
    public HttpResponse<Integer> requestPayment(@Body PaymentRequestDTO request) {
        log.info("Processing payment");

        callBackService.processPayment(request);

        return HttpResponse.status(HttpStatus.OK);
    }

}
