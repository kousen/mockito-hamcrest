package com.oreilly.astro;

import com.google.gson.Gson;
import com.oreilly.astro.json.AstroResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@SuppressWarnings("HttpUrlsUsage")
public class AstroGatewayHttpClient implements Gateway<AstroResponse> {
    private static final String DEFAULT_URL = "http://api.open-notify.org/";
    private final String url;

    public AstroGatewayHttpClient() {
        this(DEFAULT_URL);
    }

    public AstroGatewayHttpClient(String url) {
        this.url = url;
    }

    @Override
    public AstroResponse getResponse() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "astros.json"))
                .timeout(Duration.ofSeconds(2))
                .build();
        try {
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
            return new Gson().fromJson(response.body(), AstroResponse.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
