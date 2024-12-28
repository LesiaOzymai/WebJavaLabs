package com.example.spacecatsmarket.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ExternalApiService {

    private final WebClient webClient;

    public ExternalApiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://example.com").build();
    }

    public String fetchDataFromApi(String url) {

        Mono<String> response = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class);

        return response.block();
    }
}
