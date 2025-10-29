package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.LoanRequest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiService {
    private final String API_URL = "https://run.mocky.io/v3/9108b1da-beec-409e-ae14-e212003666c";
    private final HttpClient client;
    private final ObjectMapper objectMapper;

    public ApiService(){
        this.client = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public LoanRequest fetchLoanRequest() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API_URL)).GET().build();

        HttpResponse<String> response = this.client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            return null;
        }

        return objectMapper.readValue(response.body(), LoanRequest.class);
    }
}
