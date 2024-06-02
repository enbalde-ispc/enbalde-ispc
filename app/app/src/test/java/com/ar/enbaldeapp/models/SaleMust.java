package com.ar.enbaldeapp.models;

import static com.ar.enbaldeapp.support.Constants.FIRST_NAME;
import static com.ar.enbaldeapp.support.Constants.LAST_NAME;
import static com.ar.enbaldeapp.support.Constants.SALE_DATE;
import static com.ar.enbaldeapp.support.Constants.SALE_ID;
import static com.ar.enbaldeapp.support.Constants.SALE_PAYMENT_TYPE;
import static com.ar.enbaldeapp.support.Constants.SALE_SELECTIONS;
import static com.ar.enbaldeapp.support.Constants.SALE_SHIPPING;
import static com.ar.enbaldeapp.support.Constants.SALE_TOTAL;
import static com.ar.enbaldeapp.support.Constants.SALE_TRANSACTION;
import static com.ar.enbaldeapp.support.Constants.SALE_USER;
import static com.ar.enbaldeapp.support.Constants.SHIPPING_METHOD_COST;
import static com.ar.enbaldeapp.support.Constants.SHIPPING_METHOD_NAME;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class SaleMust {
    @Test
    public void beCreatedCorrectly_whenInformationIsCorrect() {
        Sale sut = new Sale(SALE_ID, SALE_USER, SALE_DATE, SALE_SELECTIONS, SALE_TOTAL, SALE_SHIPPING, SALE_PAYMENT_TYPE, SALE_TRANSACTION);

        assertEquals(SALE_ID, sut.getId());
        MatcherAssert.assertThat(sut.getClient(), CoreMatchers.containsString(FIRST_NAME));
        MatcherAssert.assertThat(sut.getClient(), CoreMatchers.containsString(LAST_NAME));
        assertEquals(SALE_DATE, sut.getDate());
        MatcherAssert.assertThat(sut.getSelections(), is(SALE_SELECTIONS));
        assertEquals(SALE_TOTAL, sut.getTotal(), 0.001);
        assertEquals(SHIPPING_METHOD_NAME, sut.getShippingMethod());
        assertEquals(SHIPPING_METHOD_COST, sut.getShippingCost(), 0.001);
        assertEquals(SALE_PAYMENT_TYPE, sut.getPaymentType());
        assertEquals(SALE_TRANSACTION, sut.getTransaction());
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
        Exception exception = assertThrows(RuntimeException.class, () -> new Sale(invalidId, SALE_USER, SALE_DATE, SALE_SELECTIONS, SALE_TOTAL, SALE_SHIPPING, SALE_PAYMENT_TYPE, SALE_TRANSACTION));
        assertEquals("El id es inválido", exception.getMessage());
    }

    @Test
    public void throwException_whenClientIsInvalid() {
        Exception exception = assertThrows(RuntimeException.class, () -> new Sale(SALE_ID, null, SALE_DATE, SALE_SELECTIONS, SALE_TOTAL, SALE_SHIPPING, SALE_PAYMENT_TYPE, SALE_TRANSACTION));;
        assertEquals("El usuario es inválido", exception.getMessage());
    }

    @Test
    public void throwException_whenSelectionsAreInvalid() {
        Exception exception = assertThrows(RuntimeException.class, () -> new Sale(SALE_ID, SALE_USER, SALE_DATE, null, SALE_TOTAL, SALE_SHIPPING, SALE_PAYMENT_TYPE, SALE_TRANSACTION));;
        assertEquals("Las selecciones son inválidas", exception.getMessage());
    }

    @Test
    public void throwException_whenTotalIsInvalid() {
        Exception exception = assertThrows(RuntimeException.class, () -> new Sale(SALE_ID, SALE_USER, SALE_DATE, SALE_SELECTIONS, -1, SALE_SHIPPING, SALE_PAYMENT_TYPE, SALE_TRANSACTION));;
        assertEquals("El total es inválido", exception.getMessage());
    }

    @Test
    public void throwException_whenShippingIsInvalid() {
        Exception exception = assertThrows(RuntimeException.class, () -> new Sale(SALE_ID, SALE_USER, SALE_DATE, SALE_SELECTIONS, SALE_TOTAL, null, SALE_PAYMENT_TYPE, SALE_TRANSACTION));;
        assertEquals("El envío es inválido", exception.getMessage());
    }

    @Test
    public void throwException_whenTransactionIsInvalid() {
        Exception exception = assertThrows(RuntimeException.class, () -> new Sale(SALE_ID, SALE_USER, SALE_DATE, SALE_SELECTIONS, SALE_TOTAL, SALE_SHIPPING, SALE_PAYMENT_TYPE, null));;
        assertEquals("La transacción es inválida", exception.getMessage());
    }
}