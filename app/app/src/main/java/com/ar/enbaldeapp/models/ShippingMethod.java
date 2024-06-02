package com.ar.enbaldeapp.models;

import com.google.gson.annotations.SerializedName;

public class ShippingMethod {
    private final long id;
    @SerializedName("nombre")
    private final String name;
    @SerializedName("monto")
    private final double cost;

    public ShippingMethod(long id, String name, double cost) {
        if (id <= 0) throw new RuntimeException("El id es inválido");
        if (name == null || name.trim().isEmpty())
            throw new RuntimeException("El nombre es inválido");
        if (cost < 0) throw new RuntimeException("El costo es inválido");

        this.id = id;
        this.name = name;
        this.cost = cost;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }
}
