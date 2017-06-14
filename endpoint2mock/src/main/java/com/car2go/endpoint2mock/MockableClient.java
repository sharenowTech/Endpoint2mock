package com.car2go.endpoint2mock;

import java.io.IOException;

import retrofit.android.AndroidApacheClient;
import retrofit.client.Client;
import retrofit.client.Request;
import retrofit.client.Response;

public class MockableClient implements Client {

    private final Client realClient;
    private final Client mockClient;
    private final BooleanFunction mockWhenFunction;

    public static MockableClient.Builder build(String mockEndpointUrl) {
        return new Builder(mockEndpointUrl);
    }

    MockableClient(Client realClient,
                   Client mockClient,
                   BooleanFunction mockWhenFunction) {
        this.realClient = realClient;
        this.mockClient = mockClient;
        this.mockWhenFunction = mockWhenFunction;
    }

    @Override
    public Response execute(Request request) throws IOException {
        return realClient.execute(request);
    }

    public static class Builder {

        private final String mockEndpointUrl;

        private Client realClient;
        private BooleanFunction mockWhenFunction = BooleanFunction.TRUE;

        Builder(String mockEndpointUrl) {
            this.mockEndpointUrl = mockEndpointUrl;
        }

        /**
         * @param realClient the client which will be used for non-mocked requests.
         */
        public Builder realClient(Client realClient) {
            this.realClient = realClient;

            return this;
        }

        /**
         * There might be cases when you do not always want to mock requests even if they are
         * annotated with {@link MockedEndpoint}. This method allows you to decide whether such
         * requests should be mocked or not by providing function which will return `true` if
         * request should be mocked and `false` if it should not.
         * <p>
         * Note, this function would be still called only for endpoints annotated with
         * {@link BooleanFunction}.
         */
        public Builder mockWhen(BooleanFunction function) {
            this.mockWhenFunction = function;

            return this;
        }

        /**
         * @return new {@link MockableClient}.
         */
        public MockableClient build() {
            if (realClient == null) {
                realClient = new AndroidApacheClient();
            }

            return new MockableClient(
                    realClient,
                    new ReplaceEndpointClient(mockEndpointUrl, realClient),
                    mockWhenFunction
            );
        }

    }

}
