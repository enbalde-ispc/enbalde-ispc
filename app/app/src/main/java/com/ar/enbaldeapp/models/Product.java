package com.ar.enbaldeapp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Product implements Serializable {
    private final long id;
    @SerializedName("nombre")
    private final String name;
    @SerializedName("descripcion")
    private final String description;
    @SerializedName("precio")
    private final double price;
    @SerializedName("cantidad")
    private final int quantity;
    @SerializedName("imagen")
    private final String image;
    @SerializedName("tipo")
    private final long type;

    public Product(long id, String name, String description, double price, int quantity, String image, long type) {
        if (id <= 0) throw new RuntimeException("El id es inválido");
        if (name == null || name.trim().isEmpty()) throw new RuntimeException("El nombre es inválido");
        if (description == null || description.trim().isEmpty()) throw new RuntimeException("La descripción es inválida");
        if (price <= 0) throw new RuntimeException("El precio es inválido");
        if (quantity < 0) throw new RuntimeException("La cantidad es inválida");
        if (image == null || image.trim().isEmpty()) throw new RuntimeException("La imagen es inválida");
        //if (type == null) throw new RuntimeException("El tipo es inválido");

        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.type = type;
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public String getImage() { return image; }
    public long getType() { return type; }
}
