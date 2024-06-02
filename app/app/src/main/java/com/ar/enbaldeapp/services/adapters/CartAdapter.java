package com.ar.enbaldeapp.services.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ar.enbaldeapp.R;
import com.ar.enbaldeapp.models.Cart;
import com.ar.enbaldeapp.models.Selection;
import com.ar.enbaldeapp.services.ApiServices;
import com.ar.enbaldeapp.services.IApiServices;
import com.ar.enbaldeapp.ui.cart.CartViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.stream.Collectors;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private final Activity activityContext;
    private CartAdapter.OnClickListener onSelectionClickListener;
    private CartViewModel cart;
    private String accessToken;

    public CartAdapter(Activity activity, CartViewModel cart, String accessToken) {
        this.activityContext = activity;
        this.cart = cart;
        this.accessToken = accessToken;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)  {
        Selection selection = cart.getSelections().getValue().get(position);

        holder.bindContent(selection);

        holder.cartProductImageView.setOnClickListener(v -> {
            if (onSelectionClickListener != null) {
                onSelectionClickListener.onClick(position, selection);
            }
        });

        holder.cartProductNameTextView.setText(selection.getProduct().getName());
        holder.cartProductNameTextView.setOnClickListener(v -> {
            if (onSelectionClickListener != null) {
                onSelectionClickListener.onClick(position, selection);
            }
        });

        holder.cartProductQuantityTextView.setText(" " + selection.getQuantity() + " ");
        holder.cartProductQuantityTextView.setOnClickListener(v -> {
            if (onSelectionClickListener != null) {
                onSelectionClickListener.onClick(position, selection);
            }
        });

        holder.cartProductSubTotalTextView.setText("Subtotal: " + selection.getTotal());
        holder.cartProductSubTotalTextView.setOnClickListener(v -> {
            if (onSelectionClickListener != null) {
                onSelectionClickListener.onClick(position, selection);
            }
        });

        holder.cartAddItemButton.setOnClickListener(v -> {
            IApiServices apiServices = new ApiServices();
            apiServices.addToCart(accessToken, this.cart.getCart(), selection.getProduct(),1,
                    this::updateSelection,
                    e -> {});
        });

        holder.cartSubtractItemButton.setOnClickListener(v -> {
            IApiServices apiServices = new ApiServices();
            apiServices.addToCart(accessToken, this.cart.getCart(), selection.getProduct(),-1,
                    this::updateSelection,
                    e -> {});
        });
    }

    private void updateSelection(Selection selection) {
        List<Selection> newSelections = selection.getQuantity() < 1
                ? removeProductFromList(selection)
                : updateProductFromList(selection);

        updateSelection(newSelections);
    }

    private List<Selection> removeProductFromList(Selection selection) {
        return this.cart.getSelections().getValue()
                .stream()
                .filter(o -> o.getId() != selection.getId())
                .collect(Collectors.toList());
    }

    private List<Selection> updateProductFromList(Selection selection) {
        return  this.cart.getSelections().getValue()
                .stream()
                .map(o -> o.getId() == selection.getId() ? selection : o)
                .collect(Collectors.toList());
    }

    public interface OnClickListener {
        void onClick(int position, Selection selection);
    }

    public void updateSelection(List<Selection> selections) {
        this.cart = new CartViewModel(new Cart(this.cart.getCart().getId(), selections));
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return cart.getSelections().getValue().size();
    }

    public void setOnClickListeners(CartAdapter.OnClickListener onClickListener) {
        this.onSelectionClickListener = onClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button cartAddItemButton;
        Button cartSubtractItemButton;
        ImageView cartProductImageView;
        TextView cartProductNameTextView;
        TextView cartProductQuantityTextView;
        TextView cartProductSubTotalTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cartProductImageView = itemView.findViewById(R.id.cartProductImage);
            cartProductNameTextView = itemView.findViewById(R.id.cartProductName);
            cartProductQuantityTextView = itemView.findViewById(R.id.cartQuantity);
            cartProductSubTotalTextView = itemView.findViewById(R.id.cartSubtotal);
            cartAddItemButton = itemView.findViewById(R.id.cartAddItem);
            cartSubtractItemButton = itemView.findViewById(R.id.cartSubtractItem);
        }

        public void bindContent(Selection selection) {
            IApiServices apiServices = new ApiServices();
            Picasso.with(activityContext).load(apiServices.getUrl() + selection.getProduct().getImage()).into(cartProductImageView);
        }
    }
}