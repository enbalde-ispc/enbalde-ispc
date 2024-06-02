package com.ar.enbaldeapp.ui.cart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ar.enbaldeapp.models.Cart;
import com.ar.enbaldeapp.models.Selection;

import java.util.List;

public class CartViewModel extends ViewModel {
    private final MutableLiveData<List<Selection>> selections;
    private final Cart cart;

    public CartViewModel(Cart cart) {
        this.cart = cart;
        this.selections = new MutableLiveData<>();
        this.selections.setValue(cart.getSelections());
    }

    public Cart getCart() {
        return this.cart;
    }

    public LiveData<List<Selection>> getSelections() {
        return selections;
    }
}