package com.ar.enbaldeapp.support;

import com.ar.enbaldeapp.models.Offer;
import com.ar.enbaldeapp.models.PaymentType;
import com.ar.enbaldeapp.models.Product;
import com.ar.enbaldeapp.models.ProductType;
import com.ar.enbaldeapp.models.Selection;
import com.ar.enbaldeapp.models.ShippingMethod;
import com.ar.enbaldeapp.models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Constants {
    public static final long USER_ID = 1;
    public static final String LAST_NAME = "Agote";
    public static final String FIRST_NAME = "Luis";
    public static final String EMAIL = "luis.agote@gmail.com";
    public static final String PHONE = "1234-5678";
    public static final String ADDRESS = "123 Main St Miami, FL";
    public static final String OBSERVATIONS = "Good client";
    public static final String USERNAME = "luis";
    public static final String PASSWORD = "12345678";
    public static final int TYPE = User.Client;
    public static final String USER_JSON = "{\"id\":1,\"apellido\":\"Agote\",\"nombre\":\"Luis\",\"email\":\"luis.agote@gmail.com\",\"direccion\":\"123 Main St Miami, FL\",\"telefono\":\"1234-5678\",\"observaciones\":\"Good client\",\"usuario\":\"luis\",\"clave\":\"12345678\",\"tipo\":2}";

    public static final long PRODUCT_TYPE_ID = 1;
    public static final String PRODUCT_TYPE_NAME = "Balde";

    public static final long PRODUCT_ID = 1;
    public static final String PRODUCT_NAME = "Helado de chocolate";
    public static final String PRODUCT_DESCRIPTION = "Un helado muy rico de chocolate con chips";
    public static final double PRODUCT_PRICE = 1100;
    public static final int PRODUCT_QUANTITY = 13;
    public static final String PRODUCT_IMAGE = "http://localhost:4200/enbalde/assets/chocolate.png";
    public static final ProductType PRODUCT_TYPE = new ProductType(PRODUCT_TYPE_ID, PRODUCT_TYPE_NAME);
    public static final String PRODUCT_JSON = "{\"id\":1,\"nombre\":\"Helado de chocolate\",\"descripcion\":\"Un helado muy rico de chocolate con chips\",\"precio\":1100.0,\"cantidad\":13,\"imagen\":\"http://localhost:4200/enbalde/assets/chocolate.png\",\"tipo\":1}";

    public static final long SHIPPING_METHOD_ID = 1;
    public static final String SHIPPING_METHOD_NAME = "Hasta 3km de distancia";
    public static final double SHIPPING_METHOD_COST = 3000;

    public static final long OFFER_ID = 1;
    public static final String OFFER_NAME = "Especial Navidad";
    public static final double OFFER_DISCOUNT = 15;
    public static final Date OFFER_EXPIRATION_DATE = DateUtils.date(2024, 12, 26);

    static class DateUtils {
        public static Date date(int year, int month, int date) {
            Calendar working = GregorianCalendar.getInstance();
            working.set(year, month, date, 0, 0, 1);
            return working.getTime();
        }
    }

    public static final long SELECTION_ID = 1;
    public static final Product SELECTION_PRODUCT = new Product(PRODUCT_ID, PRODUCT_NAME, PRODUCT_DESCRIPTION, PRODUCT_PRICE, PRODUCT_QUANTITY, PRODUCT_IMAGE, PRODUCT_TYPE_ID);
    public static final int SELECTION_QUANTITY = 3;
    public static final Offer SELECTION_OFFER = new Offer(OFFER_ID, OFFER_NAME, OFFER_DISCOUNT, OFFER_EXPIRATION_DATE);
    public static final ArrayList<Offer> SELECTION_OFFERS = new ArrayList<>(Arrays.asList(SELECTION_OFFER));
    public static final double SELECTION_DISCOUNT = 1300;
    public static final double SELECTION_TOTAL = 8700;

    public static final long SALE_ID = 1;
    public static final User SALE_USER = new User(USER_ID, LAST_NAME, FIRST_NAME, EMAIL, ADDRESS, PHONE, OBSERVATIONS, USERNAME, PASSWORD, TYPE);
    public static final Date SALE_DATE = DateUtils.date(2024, 5, 2);
    public static final List<Selection> SALE_SELECTIONS = new ArrayList<>(Arrays.asList(new Selection(SELECTION_ID, SELECTION_PRODUCT, SELECTION_QUANTITY, SELECTION_OFFERS, SELECTION_DISCOUNT, SELECTION_TOTAL)));
    public static final double SALE_TOTAL = 10000;
    public static final ShippingMethod SALE_SHIPPING = new ShippingMethod(SHIPPING_METHOD_ID, SHIPPING_METHOD_NAME, SHIPPING_METHOD_COST);
    public static final PaymentType SALE_PAYMENT_TYPE = PaymentType.CASH;
    public static final String SALE_TRANSACTION = "c80c9ae7-95ed-49b7-a629-f57746f6a8aa";

    public static final String REGISTRATION_OK_JSON = "{\"mensaje\": \"Usuario registrado exitosamente\", \"data\": {\"nombre\": \"Juan\", \"apellido\": \"Perez\", \"usuario\": \"juan8\", \"direccion\": \"123 Main St\", \"telefono\": \"11112222\", \"email\": \"juan.perez@gmail.com\", \"tipo\": 2, \"observaciones\": \"\"}, \"status\": 201}";
    public static final String LOGIN_OK_JSON = "{\"mensaje\": \"Inicio de sesi\\u00f3n exitoso\", \"data\": {\"carritoActual\": 6, \"usuarioActual\": {\"id\": 10, \"usuario\": \"juan8\", \"tipo\": 2, \"nombre\": \"Juan\", \"apellido\": \"Perez\", \"email\": \"juan.perez@gmail.com\", \"direccion\": \"123 Main St\", \"telefono\": \"11112222\", \"observaciones\": \"\"}, \"accessToken\": {\"acceso\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNzE1NDg4OTAyLCJpYXQiOjE3MTU0MDI1MDIsImp0aSI6ImNjMjBkMzg4ZTA1ZTRhMDRiZmIzYzczOTdiYWJiYTlmIiwidXNlcl9pZCI6MTB9.L6vLddKpMTTjrARO3H3Yo1LIx8DNDp4taxrODs83d2M\", \"refresco\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoicmVmcmVzaCIsImV4cCI6MTcxNTQ4ODkwMiwiaWF0IjoxNzE1NDAyNTAyLCJqdGkiOiJmODJjMjM0YTg5NTY0Mjk5YTUyZmQwYzg0ODBmZjAyNCIsInVzZXJfaWQiOjEwfQ.s-eU8E182aKh1DP_kXhjNki3vDoWrwxI_94czPop2m8\"}}, \"status\": 200}";
    public static final String LOGOUT_OK_JSON = "{\"mensaje\": \"Sesi\\u00f3n terminada con \\u00e9xito\", \"data\": null, \"status\": 200}";
    public static final String LOGOUT_ERROR_JSON = "{\"mensaje\": \"Sesi\\u00f3n terminada con error\", \"data\": null, \"status\": 405}";

    public static final String ACCESS_STRING = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNzE2MDg3MDk4LCJpYXQiOjE3MTYwMDA2OTgsImp0aSI6IjY4ODhjNDQxZDYzYzRmNzNiN2Y1NTcwYmJhOTI5ZDQxIiwidXNlcl9pZCI6MX0.deVS7aR02O8dRcAM6yly3z_t5mZJF3CNzqpmJJRs2jo";
    public static final String REFRESH_STRING = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoicmVmcmVzaCIsImV4cCI6MTcxNjA4NzA5OCwiaWF0IjoxNzE2MDAwNjk4LCJqdGkiOiJlNzQ0Zjc5YmM4MDI0ODM1ODJiNDUwM2M3ZjdlMzM1ZiIsInVzZXJfaWQiOjF9.WA65VOcLNMGrMkcGJZ4AcLi4alR4ko6uC35apj5eZE0";
    public static final String TOKEN_REFRESH_JSON = "{\"acceso\":\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNzE2MDg3MDk4LCJpYXQiOjE3MTYwMDA2OTgsImp0aSI6IjY4ODhjNDQxZDYzYzRmNzNiN2Y1NTcwYmJhOTI5ZDQxIiwidXNlcl9pZCI6MX0.deVS7aR02O8dRcAM6yly3z_t5mZJF3CNzqpmJJRs2jo\",\"refresco\":\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoicmVmcmVzaCIsImV4cCI6MTcxNjA4NzA5OCwiaWF0IjoxNzE2MDAwNjk4LCJqdGkiOiJlNzQ0Zjc5YmM4MDI0ODM1ODJiNDUwM2M3ZjdlMzM1ZiIsInVzZXJfaWQiOjF9.WA65VOcLNMGrMkcGJZ4AcLi4alR4ko6uC35apj5eZE0\"}";

    public static final long CART_ID = 1;
    public static final String CATALOGUE_STRING_JSON = "[{\"id\":1,\"nombre\":\"Helado de Chocolate\",\"descripcion\":\"Helado con chispas de chocolate\",\"precio\":\"2300.00\",\"costo\":\"1400.00\",\"imagen\":\"http://localhost:8000/media/images/helado-de-chocolate.jpg\",\"cantidad\":96,\"tipo\":1},{\"id\":2,\"nombre\":\"Helado de Frutilla\",\"descripcion\":\"Helado de frutilla natural\",\"precio\":\"1500.00\",\"costo\":\"800.00\",\"imagen\":\"http://localhost:8000/media/images/helado-de-frutilla.jpg\",\"cantidad\":48,\"tipo\":3},{\"id\":3,\"nombre\":\"Palito de Vainilla\",\"descripcion\":\"Palito de vainilla de crema\",\"precio\":\"550.00\",\"costo\":\"200.00\",\"imagen\":\"http://localhost:8000/media/images/palito-de-vainilla.jpg\",\"cantidad\":47,\"tipo\":2},{\"id\":4,\"nombre\":\"Helado de Fernet\",\"descripcion\":\"Clásico helado de Fernet\",\"precio\":\"1600.00\",\"costo\":\"1000.00\",\"imagen\":\"http://localhost:8000/media/images/Fernet.jpg\",\"cantidad\":99,\"tipo\":1}]";
    public static final int CART_AMOUNT = 1;

    public static final String ADD_TO_CART_OK_JSON = "{\"id\":6,\"cantidad\":1,\"articulo\":{\"id\":1,\"nombre\":\"Helado de Chocolate\",\"descripcion\":\"Helado con chispas de chocolate\",\"precio\":\"2300.00\",\"costo\":\"1400.00\",\"imagen\":\"/media/images/helado-de-chocolate.jpg\",\"cantidad\":96,\"tipo\":1},\"ofertas\":[],\"descuento\":0,\"total\":2300.0}";
}
