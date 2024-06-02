package com.ar.enbaldeapp.models;

import static com.ar.enbaldeapp.support.Constants.PRODUCT_DESCRIPTION;
import static com.ar.enbaldeapp.support.Constants.PRODUCT_ID;
import static com.ar.enbaldeapp.support.Constants.PRODUCT_IMAGE;
import static com.ar.enbaldeapp.support.Constants.PRODUCT_JSON;
import static com.ar.enbaldeapp.support.Constants.PRODUCT_NAME;
import static com.ar.enbaldeapp.support.Constants.PRODUCT_PRICE;
import static com.ar.enbaldeapp.support.Constants.PRODUCT_QUANTITY;
import static com.ar.enbaldeapp.support.Constants.PRODUCT_TYPE_ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;

import com.google.gson.Gson;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class ProductMust {
    @Test
    public void beCreatedCorrectly_whenInformationIsCorrect() {
        Product sut = new Product(PRODUCT_ID, PRODUCT_NAME, PRODUCT_DESCRIPTION, PRODUCT_PRICE, PRODUCT_QUANTITY, PRODUCT_IMAGE, PRODUCT_TYPE_ID);

        assertEquals(PRODUCT_ID, sut.getId());
        assertEquals(PRODUCT_NAME, sut.getName());
        assertEquals(PRODUCT_DESCRIPTION, sut.getDescription());
        assertEquals(PRODUCT_PRICE, sut.getPrice(), 0.001);
        assertEquals(PRODUCT_QUANTITY, sut.getQuantity());
        assertEquals(PRODUCT_IMAGE, sut.getImage());
        assertSame(PRODUCT_TYPE_ID, sut.getType());
    }

    @DataPoints("invalid strings")
    public static String[] invalidStrings() {
        return new String[]{"", null, "   "};
    }

    @DataPoints("invalid ids")
    public static long[] invalidIds() {
        return new long[]{-1, 0};
    }

    @DataPoints("invalid prices")
    public static double[] invalidPrices() { return new double[] { -1, 0 }; }

    @Theory
    public void throwException_whenIdIsInvalid(@FromDataPoints("invalid ids") long invalidId) {
        Exception exception = assertThrows(RuntimeException.class, () -> new Product(invalidId, PRODUCT_NAME, PRODUCT_DESCRIPTION, PRODUCT_PRICE, PRODUCT_QUANTITY, PRODUCT_IMAGE, PRODUCT_TYPE_ID));
        assertEquals("El id es inválido", exception.getMessage());
    }

    @Theory
    public void throwException_whenNameIsInvalid(@FromDataPoints("invalid strings") String invalidName) {
        Exception exception = assertThrows(RuntimeException.class, () -> new Product(PRODUCT_ID, invalidName, PRODUCT_DESCRIPTION, PRODUCT_PRICE, PRODUCT_QUANTITY, PRODUCT_IMAGE, PRODUCT_TYPE_ID));
        assertEquals("El nombre es inválido", exception.getMessage());
    }

    @Theory
    public void throwException_whenDescriptionIsInvalid(@FromDataPoints("invalid strings") String invalidDescription) {
        Exception exception = assertThrows(RuntimeException.class, () -> new Product(PRODUCT_ID, PRODUCT_NAME, invalidDescription, PRODUCT_PRICE, PRODUCT_QUANTITY, PRODUCT_IMAGE, PRODUCT_TYPE_ID));
        assertEquals("La descripción es inválida", exception.getMessage());
    }

    @Theory
    public void throwException_whenPriceIsInvalid(@FromDataPoints("invalid prices") double invalidPrice) {
        Exception exception = assertThrows(RuntimeException.class, () -> new Product(PRODUCT_ID, PRODUCT_NAME, PRODUCT_DESCRIPTION, invalidPrice, PRODUCT_QUANTITY, PRODUCT_IMAGE, PRODUCT_TYPE_ID));
        assertEquals("El precio es inválido", exception.getMessage());
    }

    @Test
    public void throwException_whenQuantityIsInvalid() {
        Exception exception = assertThrows(RuntimeException.class, () -> new Product(PRODUCT_ID, PRODUCT_NAME, PRODUCT_DESCRIPTION, PRODUCT_PRICE, -1, PRODUCT_IMAGE, PRODUCT_TYPE_ID));
        assertEquals("La cantidad es inválida", exception.getMessage());
    }

    @Theory
    public void throwException_whenImageIsInvalid(@FromDataPoints("invalid strings") String invalidImage) {
        Exception exception = assertThrows(RuntimeException.class, () -> new Product(PRODUCT_ID, PRODUCT_NAME, PRODUCT_DESCRIPTION, PRODUCT_PRICE, PRODUCT_QUANTITY, invalidImage, PRODUCT_TYPE_ID));
        assertEquals("La imagen es inválida", exception.getMessage());
    }

    @Test
    public void serializeCorrectly() {
        Product sut = new Product(PRODUCT_ID, PRODUCT_NAME, PRODUCT_DESCRIPTION, PRODUCT_PRICE, PRODUCT_QUANTITY, PRODUCT_IMAGE, PRODUCT_TYPE_ID);
        String result = new Gson().toJson(sut);
        assertEquals(PRODUCT_JSON, result);
    }

    @Test
    public void deserializeCorrectly() {
        Product sut = new Gson().fromJson(PRODUCT_JSON, Product.class);

        assertEquals(PRODUCT_ID, sut.getId());
        assertEquals(PRODUCT_NAME, sut.getName());
        assertEquals(PRODUCT_DESCRIPTION, sut.getDescription());
        assertEquals(PRODUCT_PRICE, sut.getPrice(), 0.001);
        assertEquals(PRODUCT_QUANTITY, sut.getQuantity());
        assertEquals(PRODUCT_IMAGE, sut.getImage());
        assertSame(PRODUCT_TYPE_ID, sut.getType());
    }
}
