package com.ar.enbaldeapp.services;

import static com.ar.enbaldeapp.support.Constants.USERNAME;

import static org.junit.Assert.assertEquals;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

public class ApiRequestMust {
    @Test
    public void beBuiltCorrectly() {
        ApiRequest sut = new ApiRequest.Builder()
                .addContentDisposition("usuario", USERNAME)
                .addContentDisposition("clave", 12345678)
                .addBoundary("separator")
                .buildAsUrlEncodedData();

        MatcherAssert.assertThat(sut.getData(), CoreMatchers.containsString("Content-Disposition: form-data; name=\"usuario\""));
        MatcherAssert.assertThat(sut.getData(), CoreMatchers.containsString(USERNAME));
        MatcherAssert.assertThat(sut.getData(), CoreMatchers.containsString("Content-Disposition: form-data; name=\"clave\""));
        MatcherAssert.assertThat(sut.getData(), CoreMatchers.containsString("12345678"));
        MatcherAssert.assertThat(sut.getData(), CoreMatchers.containsString("separator"));
        assertEquals("separator", sut.getBoundary());
    }
}
