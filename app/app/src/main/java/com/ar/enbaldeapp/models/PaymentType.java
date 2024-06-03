package com.ar.enbaldeapp.models;

public enum PaymentType {
    CASH_TO_PAY(1),
    CASH_PAID(2),
    ENBALDE_PAGO(3),
    STRIPE_TO_PAY(4),
    STRIPE_PAID(5);

    private int value;

    PaymentType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
