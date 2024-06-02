package com.ar.enbaldeapp.models;

import static com.ar.enbaldeapp.support.Constants.OFFER_DISCOUNT;
import static com.ar.enbaldeapp.support.Constants.OFFER_EXPIRATION_DATE;
import static com.ar.enbaldeapp.support.Constants.OFFER_ID;
import static com.ar.enbaldeapp.support.Constants.OFFER_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theory;

public class OfferMust {
    @Test
    public void beCreatedCorrectly_whenInformationIsCorrect() {
        Offer sut = new Offer(OFFER_ID, OFFER_NAME, OFFER_DISCOUNT, OFFER_EXPIRATION_DATE);

        assertEquals(OFFER_ID, sut.getId());
        assertEquals(OFFER_NAME, sut.getName());
        assertEquals(OFFER_DISCOUNT, sut.getDiscount(), 0.001);
        assertEquals(OFFER_EXPIRATION_DATE, sut.getExpirationDate());
    }

    @DataPoints("invalid strings")
    public static String[] invalidStrings() {
        return new String[]{"", null, "   "};
    }

    @DataPoints("invalid ids")
    public static long[] invalidIds() {
        return new long[] { -1, 0 };
    }

    @Theory
    public void throwException_whenIdIsInvalid(@FromDataPoints("invalid ids") long invalidId) {
        Exception exception = assertThrows(RuntimeException.class, () -> new Offer(invalidId, OFFER_NAME, OFFER_DISCOUNT, OFFER_EXPIRATION_DATE));
        assertEquals("El id es inválido", exception.getMessage());
    }

    @Theory
    public void throwException_whenNameIsInvalid(@FromDataPoints("invalid strings") String invalidName) {
        Exception exception = assertThrows(RuntimeException.class, () -> new Offer(OFFER_ID, invalidName, OFFER_DISCOUNT, OFFER_EXPIRATION_DATE));
        assertEquals("El nombre es inválido", exception.getMessage());
    }

    @Test
    public void throwException_whenDiscountIsInvalid() {
        Exception exception = assertThrows(RuntimeException.class, () -> new Offer(OFFER_ID, OFFER_NAME, -1, OFFER_EXPIRATION_DATE));
        assertEquals("El descuento es inválido", exception.getMessage());
    }
}