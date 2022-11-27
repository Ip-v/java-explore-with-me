package ru.practicum.ewm.client.delete;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewm.client.dto.ViewStats;

import java.util.List;
import java.util.Map;

@Slf4j
public class BaseClient {
    protected final RestTemplate rest;

    public BaseClient(RestTemplate rest) {
        this.rest = rest;
    }

    protected <T> boolean post(String path, T body) {
        HttpEntity<T> requestEntity = new HttpEntity<>(body, getHeaders());
        ResponseEntity<Object> response = rest.exchange(path, HttpMethod.POST, requestEntity, Object.class);
        log.info("Response for POST request status code from ewm-stat is {}", response.getStatusCodeValue());
        return response.getStatusCode().is2xxSuccessful();
    }

    protected List<ViewStats> get(String path, Map<String, Object> parameters) {
        HttpEntity<List<ViewStats>> requestEntity = new HttpEntity<>(getHeaders());

        ResponseEntity<List<ViewStats>> response = rest.exchange(path, HttpMethod.GET, requestEntity,
                new ParameterizedTypeReference<>() {}, parameters);
        log.info("Response for GET request status code from ewm-stat is {}", response.getStatusCodeValue());
        return response.getBody();
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }
}
