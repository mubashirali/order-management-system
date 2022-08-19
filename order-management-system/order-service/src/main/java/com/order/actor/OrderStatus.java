package com.order.actor;

public enum OrderStatus {
    CREATED("created"),
    PAID("paid"),
    FULFILLMENT("fulfillment"),
    CLOSED("closed");

    public String getStatus() {
        return status;
    }

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }
}
