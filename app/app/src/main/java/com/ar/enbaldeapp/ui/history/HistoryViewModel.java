package com.ar.enbaldeapp.ui.history;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ar.enbaldeapp.models.Sale;

import java.util.List;

public class HistoryViewModel extends ViewModel {
    private final MutableLiveData<List<Sale>> sales;

    public HistoryViewModel(List<Sale> sales) {
        this.sales = new MutableLiveData<>();
        this.sales.setValue(sales);
    }

    public LiveData<List<Sale>> getSales() {
        return this.sales;
    }
}
