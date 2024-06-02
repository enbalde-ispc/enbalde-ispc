package com.ar.enbaldeapp.models;

import static com.ar.enbaldeapp.support.Constants.SELECTION_DISCOUNT;
import static com.ar.enbaldeapp.support.Constants.SELECTION_ID;
import static com.ar.enbaldeapp.support.Constants.SELECTION_OFFER;
import static com.ar.enbaldeapp.support.Constants.SELECTION_OFFERS;
import static com.ar.enbaldeapp.support.Constants.SELECTION_PRODUCT;
import static com.ar.enbaldeapp.support.Constants.SELECTION_QUANTITY;
import static com.ar.enbaldeapp.support.Constants.SELECTION_TOTAL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theory;

public class SelectionMust {
    @Test
    public void beCreatedCorrectly_whenInformationIsCorrect() {
        Selection sut = new Selection(SELECTION_ID, SELECTION_PRODUCT, SELECTION_QUANTITY, SELECTION_OFFERS, SELECTION_DISCOUNT, SELECTION_TOTAL);

        assertEquals(SELECTION_ID, sut.getId());
        assertEquals(SELECTION_DISCOUNT, sut.getDiscount(), 0.001);
        assertEquals(SELECTION_TOTAL, sut.getTotal(), 0.001);
        assertEquals(SELECTION_QUANTITY, sut.getQuantity());
        assertEquals(1, sut.getOffers().size());
        assertSame(SELECTION_OFFER, sut.getOffers().get(0));
        assertSame(SELECTION_PRODUCT, sut.getProduct());
    }

    @DataPoints("invalid strings")
    public static String[] invalidStrings() {
        return new String[]{"", null, "   "};
    }

    @DataPoints("invalid ids")
    public static long[] invalidIds() {
        return new long[]{-1, 0};
    }

    @DataPoints("invalid totals")
    public static double[] invalidTotals() {
        return new double[]{-1, 0};
    }

    @Theory
    public void throwException_whenIdIsInvalid(@FromDataPoints("invalid ids") long invalidId) {
        Exception exception = assertThrows(RuntimeException.class, () -> new Selection(invalidId, SELECTION_PRODUCT, SELECTION_QUANTITY, SELECTION_OFFERS, SELECTION_DISCOUNT, SELECTION_TOTAL));
        assertEquals("El id es inválido", exception.getMessage());
    }

    @Test
    public void throwException_whenProductIsInvalid() {
        Exception exception = assertThrows(RuntimeException.class, () -> new Selection(SELECTION_ID, null, SELECTION_QUANTITY, SELECTION_OFFERS, SELECTION_DISCOUNT, SELECTION_TOTAL));
        assertEquals("El producto es inválido", exception.getMessage());
    }

    @Theory
    public void throwException_whenQuantityIsInvalid(@FromDataPoints("invalid ids") int invalidQuantity) {
        Exception exception = assertThrows(RuntimeException.class, () -> new Selection(SELECTION_ID, SELECTION_PRODUCT, invalidQuantity, SELECTION_OFFERS, SELECTION_DISCOUNT, SELECTION_TOTAL));
        assertEquals("La cantidad es inválida", exception.getMessage());
    }

    @Test
    public void throwException_whenOffersIsInvalid() {
        Exception exception = assertThrows(RuntimeException.class, () -> new Selection(SELECTION_ID, SELECTION_PRODUCT, SELECTION_QUANTITY, null, SELECTION_DISCOUNT, SELECTION_TOTAL));
        assertEquals("Las ofertas son inválidas", exception.getMessage());
    }

    @Test
    public void throwException_whenDiscountIsInvalid() {
        Exception exception = assertThrows(RuntimeException.class, () -> new Selection(SELECTION_ID, SELECTION_PRODUCT, SELECTION_QUANTITY, SELECTION_OFFERS, -1, SELECTION_TOTAL));
        assertEquals("El descuento es inválido", exception.getMessage());
    }

    @Theory
    public void throwException_whenTotalIsInvalid(@FromDataPoints("invalid totals") int invalidTotal) {
        Exception exception = assertThrows(RuntimeException.class, () -> new Selection(SELECTION_ID, SELECTION_PRODUCT, SELECTION_QUANTITY, SELECTION_OFFERS, SELECTION_DISCOUNT, invalidTotal));
        assertEquals("El total es inválido", exception.getMessage());
    }
}
