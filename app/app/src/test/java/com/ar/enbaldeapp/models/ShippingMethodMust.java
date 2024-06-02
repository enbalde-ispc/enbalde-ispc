package com.ar.enbaldeapp.models;

import static com.ar.enbaldeapp.support.Constants.SHIPPING_METHOD_COST;
import static com.ar.enbaldeapp.support.Constants.SHIPPING_METHOD_ID;
import static com.ar.enbaldeapp.support.Constants.SHIPPING_METHOD_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class ShippingMethodMust {
    @Test
    public void beCreatedCorrectly_whenInformationIsCorrect() {
        ShippingMethod sut = new ShippingMethod(SHIPPING_METHOD_ID, SHIPPING_METHOD_NAME, SHIPPING_METHOD_COST);

        assertEquals(SHIPPING_METHOD_ID, sut.getId());
        assertEquals(SHIPPING_METHOD_NAME, sut.getName());
        assertEquals(SHIPPING_METHOD_COST, sut.getCost(), 0.001);
    }

    @DataPoints("invalid strings")
    public static String[] invalidStrings() {
        return new String[]{"", null, "   "};
    }

    @DataPoints("invalid ids")
    public static long[] invalidIds() {
        return new long[]{-1, 0};
    }

    @Theory
    public void throwException_whenIdIsInvalid(@FromDataPoints("invalid ids") long invalidId) {
        Exception exception = assertThrows(RuntimeException.class, () -> new ShippingMethod(invalidId, SHIPPING_METHOD_NAME, SHIPPING_METHOD_COST));
        assertEquals("El id es inválido", exception.getMessage());
    }

    @Theory
    public void throwException_whenNameIsInvalid(@FromDataPoints("invalid strings") String invalidLastName) {
        Exception exception = assertThrows(RuntimeException.class, () -> new ShippingMethod(SHIPPING_METHOD_ID, invalidLastName, SHIPPING_METHOD_COST));
        assertEquals("El nombre es inválido", exception.getMessage());
    }

    @Test
    public void throwException_whenCostIsInvalid() {
        Exception exception = assertThrows(RuntimeException.class, () -> new ShippingMethod(SHIPPING_METHOD_ID, SHIPPING_METHOD_NAME, -1));
        assertEquals("El costo es inválido", exception.getMessage());
    }
}
