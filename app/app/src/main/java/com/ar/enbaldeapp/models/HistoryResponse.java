package com.ar.enbaldeapp.models;

import java.util.List;

public class HistoryResponse {
    private final List<Sale> sales;

    public HistoryResponse(List<Sale> sales) {
        this.sales = sales;
    }

    public List<Sale> getSales() {
        return sales;
    }
}
