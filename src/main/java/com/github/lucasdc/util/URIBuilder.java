package com.github.lucasdc.util;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

public class URIBuilder {
    public static String buildUri(String baseUrl, String endpoint, Map<String, Object> params) {
        String queryString = params.entrySet().stream()
                .map(e -> URLEncoder.encode(e.getKey(), StandardCharsets.UTF_8) + "=" +
                        e.getValue())
                .collect(Collectors.joining("&"));
        return baseUrl + endpoint + "?" + queryString;
    }
}
