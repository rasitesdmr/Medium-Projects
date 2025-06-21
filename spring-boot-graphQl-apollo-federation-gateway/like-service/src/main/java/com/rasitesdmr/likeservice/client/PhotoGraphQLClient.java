package com.rasitesdmr.likeservice.client;

import com.rasitesdmr.likeservice.domain.exception.exceptions.InternalServerErrorException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
public class PhotoGraphQLClient {

    private final WebClient webClient;

    final String userGraphqlUrl = "http://localhost:8091/graphql";

    public PhotoGraphQLClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl(userGraphqlUrl)
                .build();
    }

    public boolean existsPhotoByUserId(Long photoId) {
        String query = """
            query($id: Long!) {
                existsById(id: $id)
            }
        """;

        Map<String, Object> variables = Map.of("id", photoId);

        Map<String, Object> body = Map.of(
                "query", query,
                "variables", variables
        );

        Map response = webClient.post()
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        if (response == null) throw new InternalServerErrorException("existsPhotoByUserId client map null");
        return (boolean) ((Map<?, ?>) response.get("data")).get("existsById");
    }
}
