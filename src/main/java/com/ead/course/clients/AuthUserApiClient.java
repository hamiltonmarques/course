package com.ead.course.clients;

import com.ead.course.api.response.ApiResponse;
import com.ead.course.api.response.PageResponse;
import com.ead.course.dtos.UserDTO;
import com.ead.course.exception.ExternalApiException;
import com.ead.course.specifications.UserFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class AuthUserApiClient {

    private final WebClient authUserWebClient;

    public PageResponse<UserDTO> getUsers(UserFilter filter, Pageable pageable) {
        ApiResponse<PageResponse<UserDTO>> response =
                authUserWebClient
                        .get()
                        .uri(uriBuilder -> buildUri(uriBuilder, filter, pageable))
                        .retrieve()
                        .onStatus(HttpStatus::isError,
                                clientResponse -> clientResponse
                                        .bodyToMono(String.class)
                                        .map(ExternalApiException::new))
                        .bodyToMono(new ParameterizedTypeReference<ApiResponse<PageResponse<UserDTO>>>() {
                        })
                        .block();

        if (response == null) {
            throw new ExternalApiException("AuthUser API returned an empty response");
        }

        return response.getData();
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
