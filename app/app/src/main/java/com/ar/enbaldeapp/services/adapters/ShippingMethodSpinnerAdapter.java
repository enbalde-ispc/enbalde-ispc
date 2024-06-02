package com.ar.enbaldeapp.services.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ar.enbaldeapp.R;

import java.util.List;
import java.util.function.Function;

public class ShippingMethodSpinnerAdapter<T> extends BaseAdapter {
    private ShippingMethodSpinnerAdapter.OnClickListener<T> onItemClickListener;
    private List<T> items;
    private final Function<T, Long> spinnerIdGetter;
    private final Function<T, String> spinnerTextGetter;

    public ShippingMethodSpinnerAdapter(List<T> items, Function<T, Long> spinnerIdGetter, Function<T, String> spinnerTextGetter) {
        this.items = items;
        this.spinnerIdGetter = spinnerIdGetter;
        this.spinnerTextGetter = spinnerTextGetter;
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Object getItem(int position) {
        return this.items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return spinnerIdGetter.apply(this.items.get(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_item, parent, false);
        }

        T item = this.items.get(position);
        String newText = spinnerTextGetter.apply(item);
        TextView textView = convertView.findViewById(R.id.spinnerText);

        if (! newText.equals(textView.toString())) {
            textView.setText(newText);
            if (onItemClickListener != null) {
                onItemClickListener.onClick(position, item);
            }
        }

        return convertView;
    }

    public interface OnClickListener<T> {
        void onClick(int position, T item);
    }

    public void setOnClickListeners(ShippingMethodSpinnerAdapter.OnClickListener<T> onClickListener) {
        this.onItemClickListener = onClickListener;
    }
}
