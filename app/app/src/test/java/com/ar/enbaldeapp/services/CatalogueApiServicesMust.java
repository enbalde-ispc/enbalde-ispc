package com.ar.enbaldeapp.services;

import static com.ar.enbaldeapp.support.Constants.CATALOGUE_STRING_JSON;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import com.ar.enbaldeapp.models.Product;
import com.ar.enbaldeapp.services.wrappers.ApiResponseWrapper;
import com.ar.enbaldeapp.support.ApiServicesStub;
import com.ar.enbaldeapp.support.ServerConnectorStub;

import org.junit.Test;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class CatalogueApiServicesMust {
    @Test
    public void throwException_whenSuccessCallbackIsInvalid() {
        ApiServices sut = new ApiServices();
        Exception exception = assertThrows(RuntimeException.class, () -> sut.getCatalogue(null, e -> {}));
        assertEquals("El callback por éxito es inválido", exception.getMessage());
    }

    @Test
    public void throwException_whenFailureCallbackIsInvalid() {
        ApiServices sut = new ApiServices();
        Exception exception = assertThrows(RuntimeException.class, () -> sut.getCatalogue(u -> {}, null));
        assertEquals("El callback por fallo es inválido", exception.getMessage());
    }

    @Test
    public void callSuccess_whenCatalogueIsRetrievedCorrectly() {
        AtomicBoolean called = new AtomicBoolean(false);
        IApiServices sut = new ApiServicesStub.Builder()
                .withGetCatalogueFromCallback(u -> new ServerConnectorStub.Builder<Product>()
                        .withConnectReturning(true)
                        // Muy dificil crear una prueba que llegue hasta el getCatalogue y llame al preprocess
                        // por si mismo en Java porque hay que mockear demasiadas cosas
                        .withResponse(new ApiResponse<>(new ApiResponseWrapper().preprocessResponse(HttpURLConnection.HTTP_OK, CATALOGUE_STRING_JSON)))
                        .build()
                )
                .build();

        sut.getCatalogue(s -> called.set(true), e -> {});
        assertTrue(called.get());
    }

    @Test
    public void obtainListOfProductsCorrectly() {
        AtomicReference<List<Product>> products = new AtomicReference<>();
        IApiServices sut = new ApiServicesStub.Builder()
                .withGetCatalogueFromCallback(u -> new ServerConnectorStub.Builder<Product>()
                        .withConnectReturning(true)
                        // Muy dificil crear una prueba que llegue hasta el getCatalogue y llame al preprocess
                        // por si mismo en Java porque hay que mockear demasiadas cosas
                        .withResponse(new ApiResponse<>(new ApiResponseWrapper().preprocessResponse(HttpURLConnection.HTTP_OK, CATALOGUE_STRING_JSON)))
                        .build()
                )
                .build();

        sut.getCatalogue(products::set, e -> {});
        assertEquals(4, products.get().size());
    }

    @Test
    public void callFailure_whenCatalogueCannotBeRetrieved() {
        AtomicBoolean called = new AtomicBoolean(false);
        IApiServices sut = new ApiServicesStub.Builder()
                .withGetCatalogueFromCallback(u -> new ServerConnectorStub.Builder<Product>()
                        .withConnectReturning(false)
                        .build()
                )
                .build();

        sut.getCatalogue(s -> {}, e -> called.set(true));
        assertTrue(called.get());
    }
}
