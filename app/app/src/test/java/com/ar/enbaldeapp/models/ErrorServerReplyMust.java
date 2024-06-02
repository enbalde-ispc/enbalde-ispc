package com.ar.enbaldeapp.models;

import static com.ar.enbaldeapp.support.Constants.LOGOUT_ERROR_JSON;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;

import com.ar.enbaldeapp.services.ApiError;
import com.ar.enbaldeapp.services.reply.ErrorServerReply;
import com.ar.enbaldeapp.support.DummyRequester;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ErrorServerReplyMust {
    @Test
    public void throwException_whenInputStreamIsInvalid() {
        Exception exception = assertThrows(RuntimeException.class, () -> new ErrorServerReply<>(null, 400, new DummyRequester<>()));
        assertEquals("El input stream es inválido", exception.getMessage());
    }

    @Test
    public void throwException_whenRequesterIsInvalid() {
        try (InputStream input = new ByteArrayInputStream(LOGOUT_ERROR_JSON.getBytes(StandardCharsets.UTF_8))) {
            Exception exception = assertThrows(RuntimeException.class, () -> new ErrorServerReply<>(input, 400, null));
            assertEquals("El requester es inválido", exception.getMessage());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void alwaysReturnNullResponse() throws IOException {
        try (InputStream input = new ByteArrayInputStream(LOGOUT_ERROR_JSON.getBytes(StandardCharsets.UTF_8))) {
            ErrorServerReply<String> sut = new ErrorServerReply<>(input, 400, new DummyRequester<>());
            assertNull(sut.getResponse());
        }
    }

    @Test
    public void alwaysReturnFalse() {
        try (InputStream input = new ByteArrayInputStream(LOGOUT_ERROR_JSON.getBytes(StandardCharsets.UTF_8))) {
            ErrorServerReply<String> sut = new ErrorServerReply<>(input, 400, new DummyRequester<>());
            assertFalse(sut.getReturnValue());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void generateApiErrorCorrectly() {
        try (InputStream input = new ByteArrayInputStream(LOGOUT_ERROR_JSON.getBytes(StandardCharsets.UTF_8))) {
            ErrorServerReply<String> sut = new ErrorServerReply<>(input, 400, new DummyRequester<>());
            ApiError result = sut.getError();
            assertEquals("Sesión terminada con error", result.getMessage());
        } catch (IOException e) {
            fail();
        }
    }
}

