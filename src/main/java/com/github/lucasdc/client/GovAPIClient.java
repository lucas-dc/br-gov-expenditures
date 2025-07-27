package com.github.lucasdc.client;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lucasdc.config.GovAPIProperties;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@Component
public class GovAPIClient {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();
    private final GovAPIProperties props;

    private static final String API_KEY_HEADER = "chave-api-dados";

    public GovAPIClient(GovAPIProperties props) {
        this.props = props;
    }

    public <T> List<T> get(String endpoint, Map<String, String> params, Class<T> reponseType) {
        String uri = URIBuilder.buildUri(props.getBaseURL(), endpoint, params);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header(API_KEY_HEADER, props.getToken())
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, reponseType);
            return mapper.readValue(response.body(), type);
        } catch (Exception e) {
            throw new RuntimeException("Failed to call Gov API", e);
        }
    }
}
