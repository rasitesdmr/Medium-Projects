package com.rasitesdmr.photo_service.client;

import com.rasitesdmr.photo_service.domain.exception.exceptions.InternalServerErrorException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
public class UserGraphQLClient {

    private final WebClient webClient;

    final String userGraphqlUrl = "http://localhost:8090/graphql";

    public UserGraphQLClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl(userGraphqlUrl)
                .build();
    }

    public boolean existsUserByUserId(Long userId) {
        String query = """
            query($id: Long!) {
                existsById(id: $id)
            }
        """;

        Map<String, Object> variables = Map.of("id", userId);

        Map<String, Object> body = Map.of(
                "query", query,
                "variables", variables
        );

        Map response = webClient.post()
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        if (response == null) throw new InternalServerErrorException("existUserByUserId client map null");
        return (boolean) ((Map<?, ?>) response.get("data")).get("existsById");
    }


}
