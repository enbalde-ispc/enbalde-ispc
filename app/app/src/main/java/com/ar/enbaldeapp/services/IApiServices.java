package com.ar.enbaldeapp.services;

import com.ar.enbaldeapp.models.Cart;
import com.ar.enbaldeapp.models.PaymentType;
import com.ar.enbaldeapp.models.Product;
import com.ar.enbaldeapp.models.Sale;
import com.ar.enbaldeapp.models.Selection;
import com.ar.enbaldeapp.models.ShippingMethod;
import com.ar.enbaldeapp.models.User;
import com.ar.enbaldeapp.models.UserToken;

import java.util.List;
import java.util.function.Consumer;

public interface IApiServices {
    String getUrl();
    void login(String username, String password, Consumer<UserToken> onSuccess, Consumer<ApiError> onFailure);
    void logout(String accessToken, Consumer<String> onSuccess, Consumer<ApiError> onFailure);
    void register(String firstName, String lastName, String email, String address, String phoneNumber, String username, String password, Consumer<User> onSuccess, Consumer<ApiError> onFailure);
    void getCatalogue(Consumer<List<Product>> onSuccess, Consumer<ApiError> onFailure);
    void getCart(String accessToken, long cartId, Consumer<Cart> onSuccess, Consumer<ApiError> onFailure);
    void addToCart(String accessToken, Cart cart, Product product, int amount, Consumer<Selection> onSuccess, Consumer<ApiError> onFailure);
    void sendRecoveryToken(String email, Consumer<String> onSuccess, Consumer<ApiError> onFailure);
    void resetPassword(String accessToken, String newPassword, Consumer<String> onSuccess, Consumer<ApiError> onFailure);
    void getHistory(String accessToken, User user, Consumer<List<Sale>> onSuccess, Consumer<ApiError> onFailure);
    void modifyUser(String accessToken, User user, String address, String email, String oldPassword, String newPassword, String repeatPassword, String phone, Consumer<User> onSuccess, Consumer<ApiError> onFailure);
    void getShippingMethods(String accessToken, Consumer<List<ShippingMethod>> onSuccess, Consumer<ApiError> onFailure);
    void checkout(String accessToken, Cart cart, ShippingMethod shippingMethod, PaymentType paymentType, String transaction, Consumer<Sale> onSuccess, Consumer<ApiError> onFailure);
    void replaceCart(String accessToken, User user, Consumer<Long> onSuccess, Consumer<ApiError> onFailure);
}
