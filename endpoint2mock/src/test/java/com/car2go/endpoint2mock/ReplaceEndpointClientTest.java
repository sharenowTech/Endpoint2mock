package com.car2go.endpoint2mock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import retrofit.client.Client;
import retrofit.client.Request;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ReplaceEndpointClientTest {

    @Mock
    Client realClient;

    ReplaceEndpointClient testee;

    @Before
    public void setUp() throws Exception {
        testee = new ReplaceEndpointClient(
                "http://replaced.com",
                realClient
        );
    }

    @Test
    public void replaceUrl() throws Exception {
        // Given
        Request request = new Request("GET", "https://original.com/path1/path2", null, null);

        // When
        testee.execute(request);

        // Then
        verify(realClient).execute(argThat(new ArgumentMatcher<Request>() {
            @Override
            public boolean matches(Request argument) {
                return argument.getUrl().equals("http://replaced.com/path1/path2");
            }
        }));
    }

}