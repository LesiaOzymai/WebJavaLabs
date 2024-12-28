package com.example.spacecatsmarket;

import com.example.spacecatsmarket.service.ExternalApiService;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.github.tomakehurst.wiremock.client.WireMock;
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

        WireMock.stubFor(get(urlEqualTo("/api/products/999"))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"error\": \"Product not found\" }")));

        WireMock.stubFor(get(urlEqualTo("/api/products/500"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"error\": \"Internal server error\" }")));

        String response = externalApiService.fetchDataFromApi("/api/products/123");
        assert response.contains("Product Name");
        String response404 = externalApiService.fetchDataFromApi("/api/products/999");
        assert response404.contains("error");
        String response500 = externalApiService.fetchDataFromApi("/api/products/500");
        assert response500.contains("error");
    }
}
