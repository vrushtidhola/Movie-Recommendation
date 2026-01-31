package com.personal.movie_recommendation_web.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.Map;

@Service
public class TmdbService {
    private final WebClient webClient;
    private final String apiKey;

    public TmdbService(@Value("${tmdb.base.url}") String baseUrl,
                       @Value("${tmdb.api.key}") String apiKey) {
        System.out.println("TMDB BASE URL = " + baseUrl);
        System.out.println("TMDB API KEY LOADED = " + (apiKey != null));

        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
        this.apiKey = apiKey;
    }

    public Map<String, Object> getTrending(String timeWindow){
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/trending/movie/{timeWindow}")
                        .queryParam("api_key", apiKey)
                        .build(timeWindow))
                .retrieve().bodyToMono(Map.class).block();
    }

    public Map<String, Object> searchMovie(String q, int page){
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/search/movie")
                        .queryParam("api_key", apiKey)
                        .queryParam("query", q)
                        .queryParam("page", page)
                        .build())
                .retrieve().bodyToMono(Map.class).block();
    }

    public Map<String, Object> getDetails(Long tmdbId){
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/movie/{id}")
                        .queryParam("api_key", apiKey)
                        .queryParam("append_to_response", "credits")
                        .build(tmdbId))
                .retrieve().bodyToMono(Map.class).block();
    }
}
