package com.ar.enbaldeapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Sale {
    private final long id;
    @SerializedName("cliente")
    private final String client;
    @SerializedName("fecha")
    private final Date date;
    @SerializedName("selecciones")
    private final List<Selection> selections;
    private final double total;
    @SerializedName("envio")
    private final String shipping;
    private final double shippingCost;
    @SerializedName("pago")
    private int payment;
    private final PaymentType paymentType;
    @SerializedName("transaccion")
    private final String transaction;

    public Sale(long id, User user, Date date, List<Selection> selections, double total, ShippingMethod shipping, PaymentType paymentType, String transaction) {
        if (id <= 0) throw new RuntimeException("El id es inválido");
        if (user == null) throw new RuntimeException("El usuario es inválido");
        if (selections == null) throw new RuntimeException("Las selecciones son inválidas");
        if (total < 0) throw new RuntimeException("El total es inválido");
        if (shipping == null) throw new RuntimeException("El envío es inválido");
        if (transaction == null) throw new RuntimeException("La transacción es inválida");

        this.id = id;
        this.client = user.getFirstName() + " " + user.getLastName();
        this.date = date;
        this.selections = selections;
        this.total = total;
        this.shipping = shipping.getName();
        this.shippingCost = shipping.getCost();
        this.paymentType = paymentType;
        this.transaction = transaction;
    }

    public long getId() { return id; }
    public String getClient() { return client; }
    public Date getDate() { return date; }
    public List<Selection> getSelections() { return selections; }
    public double getTotal() { return total; }
    public String getShippingMethod() { return shipping; }
    public double getShippingCost() { return shippingCost; }
    public PaymentType getPaymentType() { return paymentType; }
    public String getTransaction() { return transaction; }

    public String getPaymentAsString() {
        switch (payment) {
            case 1: return "Efectivo a pagar";
            case 2: return "Efectivo pagado";
            case 3: return "Enbalde Pago";
            case 4: return "Stripe Pago a pagar";
            case 5: return "Stripe pagado";
            default: return "Desconocido";
        }
    }
}
