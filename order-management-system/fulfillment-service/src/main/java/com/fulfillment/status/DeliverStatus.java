package com.fulfillment.status;

import java.util.Random;

public enum DeliverStatus {
    SUCCESS, FAILURE;

    public static DeliverStatus getStatus() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }

}
