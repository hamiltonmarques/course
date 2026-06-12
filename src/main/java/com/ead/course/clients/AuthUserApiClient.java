package com.ead.course.clients;

import com.ead.course.api.response.ApiResponse;
import com.ead.course.api.response.PageResponse;
import com.ead.course.dtos.UserDTO;
import com.ead.course.exception.ExternalApiException;
import com.ead.course.specifications.UserFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthUserApiClient {

    private final WebClient authUserWebClient;

    @Value("${app.clients.auth-user-api.label}")
    String apiLabel;

    public PageResponse<UserDTO> getUsers(UserFilter filter, Pageable pageable) {
        log.info("getting users from {}...", apiLabel);
        ApiResponse<PageResponse<UserDTO>> apiResponse =
                authUserWebClient
                        .get()
                        .uri(uriBuilder -> buildUri(uriBuilder, filter, pageable))
                        .retrieve()
                        .onStatus(HttpStatus::isError,
                                response -> response
                                        .bodyToMono(new ParameterizedTypeReference<ApiResponse<Object>>() {
                                        })
                                        .map(error -> new ExternalApiException(
                                                apiLabel,
                                                response.statusCode(),
                                                error))
                        )
                        .bodyToMono(new ParameterizedTypeReference<ApiResponse<PageResponse<UserDTO>>>() {
                        })
                        .block();

        return Objects.requireNonNull(apiResponse).getData();
    }

    public UserDTO getUser(UUID id) {
        log.info("getting user from {}...", apiLabel);
        ApiResponse<UserDTO> apiResponse =
                authUserWebClient
                        .get()
                        .uri("/users/{id}", id)
                        .retrieve()
                        .onStatus(HttpStatus::isError,
                                response -> response
                                        .bodyToMono(new ParameterizedTypeReference<ApiResponse<Object>>() {
                                        })
                                        .map(error -> new ExternalApiException(
                                                apiLabel,
                                                response.statusCode(),
                                                error))
                        )
                        .bodyToMono(new ParameterizedTypeReference<ApiResponse<UserDTO>>() {
                        })
                        .block();

        return Objects.requireNonNull(apiResponse).getData();
    }

    public void subscribeUserInCourse(UUID userId, UUID courseId) {
        log.info("subscribing user in course at {}...", apiLabel);
        authUserWebClient
                .post()
                .uri("/user/{userId}/subscribe/{courseId}", userId, courseId)
                .retrieve()
                .onStatus(HttpStatus::isError,
                        response -> response
                                .bodyToMono(new ParameterizedTypeReference<ApiResponse<Object>>() {
                                })
                                .map(error -> new ExternalApiException(
                                        apiLabel,
                                        response.statusCode(),
                                        error))
                )
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<UUID>>() {
                })
                .block();
    }

    private URI buildUri(UriBuilder uriBuilder, UserFilter filter, Pageable pageable) {
        uriBuilder.path("/users");

        addIfPresent(uriBuilder, "type", filter.getType());
        addIfPresent(uriBuilder, "status", filter.getStatus());
        addIfPresent(uriBuilder, "email", filter.getEmail());
        addIfPresent(uriBuilder, "courseId", filter.getCourseId());

        uriBuilder.queryParam("page", pageable.getPageNumber());
        uriBuilder.queryParam("size", pageable.getPageSize());

        pageable.getSort().forEach(order ->
                uriBuilder.queryParam(
                        "sort",
                        order.getProperty() + "," + order.getDirection()));

        return uriBuilder.build();
    }

    private void addIfPresent(UriBuilder uriBuilder, String paramName, Object value) {
        if (value != null) {
            uriBuilder.queryParam(paramName, value);
        }
    }
}
