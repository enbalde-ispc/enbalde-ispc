package com.ar.enbaldeapp.models;

import static com.ar.enbaldeapp.support.Constants.LOGIN_OK_JSON;
import static com.ar.enbaldeapp.support.Constants.LOGOUT_ERROR_JSON;
import static com.ar.enbaldeapp.support.Constants.LOGOUT_OK_JSON;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.ar.enbaldeapp.services.ApiError;
import com.ar.enbaldeapp.services.ApiResponse;
import com.ar.enbaldeapp.services.reply.ErrorServerReply;
import com.ar.enbaldeapp.services.reply.OkServerReply;
import com.ar.enbaldeapp.support.DummyRequester;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class OkServerReplyMust {
    @Test
    public void throwException_whenInputStreamIsInvalid() {
        Exception exception = assertThrows(RuntimeException.class, () -> new OkServerReply<>(null, 200, new DummyRequester<>()));
        assertEquals("El input stream es inválido", exception.getMessage());
    }

    @Test
    public void throwException_whenRequesterIsInvalid() {
        try (InputStream input = new ByteArrayInputStream(LOGOUT_ERROR_JSON.getBytes(StandardCharsets.UTF_8))) {
            Exception exception = assertThrows(RuntimeException.class, () -> new OkServerReply<>(input, 200, null));
            assertEquals("El requester es inválido", exception.getMessage());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void alwaysReturnNullError() throws IOException {
        try (InputStream input = new ByteArrayInputStream(LOGIN_OK_JSON.getBytes(StandardCharsets.UTF_8))) {
            OkServerReply<User> sut = new OkServerReply<>(input, 200, new DummyRequester<>());
            assertNull(sut.getError());
        }
    }

    @Test
    public void alwaysReturnTrue() {
        try (InputStream input = new ByteArrayInputStream(LOGIN_OK_JSON.getBytes(StandardCharsets.UTF_8))) {
            OkServerReply<User> sut = new OkServerReply<>(input, 200, new DummyRequester<>());
            assertTrue(sut.getReturnValue());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void generateApiErrorCorrectly() {
        try (InputStream input = new ByteArrayInputStream(LOGIN_OK_JSON.getBytes(StandardCharsets.UTF_8))) {
            OkServerReply<User> sut = new OkServerReply<>(input, 200, new DummyRequester<>());

            ApiResponse<User> result = sut.getResponse();
            assertEquals("Inicio de sesión exitoso", result.getMessage());
            assertEquals(200, result.getStatus());
            assertNotNull(result.castResponseAs(User.class));
        } catch (IOException e) {
            fail();
        }
    }
}
