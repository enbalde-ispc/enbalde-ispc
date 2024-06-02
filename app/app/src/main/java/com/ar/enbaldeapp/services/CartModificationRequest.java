package com.ar.enbaldeapp.services;

import com.ar.enbaldeapp.models.Product;
import com.google.gson.annotations.SerializedName;

public class CartModificationRequest {
    @SerializedName("articulo")
    private final long productId;
    @SerializedName("cantidad")
    private final int quantity;

    public CartModificationRequest(Product product, int quantity) {
        this.productId = product.getId();
        this.quantity = quantity;
    }
}
