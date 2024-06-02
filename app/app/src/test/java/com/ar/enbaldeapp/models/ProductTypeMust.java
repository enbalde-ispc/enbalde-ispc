package com.ar.enbaldeapp.models;

import static com.ar.enbaldeapp.support.Constants.PRODUCT_TYPE_ID;
import static com.ar.enbaldeapp.support.Constants.PRODUCT_TYPE_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class ProductTypeMust {
    @Test
    public void beCreatedCorrectly_whenInformationIsCorrect() {
        ProductType sut = new ProductType(PRODUCT_TYPE_ID, PRODUCT_TYPE_NAME);

        assertEquals(PRODUCT_TYPE_ID, sut.getId());
        assertEquals(PRODUCT_TYPE_NAME, sut.getName());
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
        Exception exception = assertThrows(RuntimeException.class, () -> new ProductType(invalidId, PRODUCT_TYPE_NAME));
        assertEquals("El id es inválido", exception.getMessage());
    }

    @Theory
    public void throwException_whenNameIsInvalid(@FromDataPoints("invalid strings") String invalidName) {
        Exception exception = assertThrows(RuntimeException.class, () -> new ProductType(PRODUCT_TYPE_ID, invalidName));
        assertEquals("El nombre es inválido", exception.getMessage());
    }
}
