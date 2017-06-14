package com.car2go.endpoint2mock;

import java.io.IOException;

import retrofit.client.Client;
import retrofit.client.Request;
import retrofit.client.Response;

/**
 * Client which replaces endpoint with another one and then calls the real client.
 */
class ReplaceEndpointClient implements Client {

    private final String newEndpoint;
    private final Client realClient;

    ReplaceEndpointClient(String newEndpoint,
                          Client realClient) {
        this.newEndpoint = newEndpoint;
        this.realClient = realClient;
    }

    @Override
    public Response execute(Request request) throws IOException {
        return null;
    }

}
