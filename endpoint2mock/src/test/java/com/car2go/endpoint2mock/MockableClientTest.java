package com.car2go.endpoint2mock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import retrofit.client.Client;
import retrofit.client.Request;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class MockableClientTest {

    static final Request REQUEST = new Request(
            "GET",
            "http://example.com",
            null,
            null
    );

    @Mock
    Registry registry;
    @Mock
    Client realClient;
    @Mock
    Client mockClient;
    @Mock
    BooleanFunction lookupFunction;

    MockableClient testee;

    @Before
    public void setUp() throws Exception {
        testee = new MockableClient(
                registry,
                realClient,
                mockClient,
                lookupFunction
        );
    }

    @Test
    public void callRealClient_NotInRegistry() throws Exception {
        // Given
        givenEndpointInRegistry(false);

        // When
        testee.execute(REQUEST);

        // Then
        verifyRealClientCalled();
        verifyZeroInteractions(lookupFunction);
    }

    @Test
    public void callRealClient_WhenLookupFalse_InRegistry() throws Exception {
        // Given
        givenEndpointInRegistry(true);
        givenWhenFunctionReturns(false);

        // When
        testee.execute(REQUEST);

        // Then
        verifyRealClientCalled();
    }

    @Test
    public void callMockClient_WhenLookupTrue_InRegistry() throws Exception {
        // Given
        givenEndpointInRegistry(true);
        givenWhenFunctionReturns(true);

        // When
        testee.execute(REQUEST);

        // Then
        verifyMockClientCalled();
    }

    private void verifyRealClientCalled() throws java.io.IOException {
        verify(realClient).execute(REQUEST);
        verifyZeroInteractions(mockClient);
    }

    private void verifyMockClientCalled() throws java.io.IOException {
        verify(mockClient).execute(REQUEST);
        verifyZeroInteractions(realClient);
    }

    private void givenEndpointInRegistry(boolean result) {
        given(registry.isInRegistry(anyString()))
                .willReturn(result);
    }

    private void givenWhenFunctionReturns(boolean result) {
        given(lookupFunction.call())
                .willReturn(result);
    }

}