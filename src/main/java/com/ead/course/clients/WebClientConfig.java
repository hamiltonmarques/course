package com.ead.course.clients;

import com.ead.course.exception.ExternalApiUnavailableException;
import io.netty.channel.ChannelOption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class WebClientConfig {

    final HttpClient HTTP_CLIENT = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .responseTimeout(Duration.ofSeconds(10));

    @Bean
    public WebClient courseWebClient(
            @Value("${app.clients.auth-user-api.base-url}") String baseUrl,
            @Value("${app.clients.auth-user-api.label}") String apiLabel) {

        return WebClient.builder()
                .baseUrl(baseUrl)
                .clientConnector(new ReactorClientHttpConnector(HTTP_CLIENT))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .filter(errorFilter(apiLabel))
                .build();
    }

    private ExchangeFilterFunction errorFilter(String apiLabel) {
        return (request, next) ->
                next.exchange(request)
                        .onErrorMap(
                                WebClientRequestException.class,
                                ex -> new ExternalApiUnavailableException(apiLabel, ex)
                        );
    }
}
