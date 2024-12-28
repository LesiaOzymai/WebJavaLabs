package com.example.spacecatsmarket;

import com.example.spacecatsmarket.service.ExternalApiService;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@WireMockTest(httpPort = 8080)
@SpringBootTest
public class ExternalApiServiceTest {

    @Autowired
    private ExternalApiService externalApiService;

    @Test
    public void testFetchDataFromApi() {
        WireMock.stubFor(get(urlEqualTo("/api/products/123"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("product-response.json")));

        String response = externalApiService.fetchDataFromApi("/api/products/123");

        assert response.contains("Product Name");
    }
}
