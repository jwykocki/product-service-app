package com.jw.contract;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.model.RequestResponsePact;

import java.util.HashMap;
import java.util.Map;

@PactTestFor(providerName = "test_provider", hostInterface="localhost")
public class PactOrderReservationTest {

    @Pact(consumer = "test_consumer")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        return builder
                .given("test POST")
                .uponReceiving("POST REQUEST")
                .path("/reserve")
                .method("POST")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body("{\"condition\": true, \"name\": \"tom\"}").toPact();
    }


}
