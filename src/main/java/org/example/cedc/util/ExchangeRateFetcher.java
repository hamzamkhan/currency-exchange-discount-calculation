package org.example.cedc.util;

import lombok.extern.slf4j.Slf4j;
import org.example.cedc.exception.ServiceLayerException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * @author hamza mustafa khan
 * @mailto : hamzamkhan@outlook.com
 */

@Slf4j
@Component
public class ExchangeRateFetcher {

    private static final String PAIR_CONVERSION = "/pair/%s/%s";

    @Value("${exchange.rate.api.url}")
    private String apiUrl;

    @Value("${exchange.rate.api.key}")
    private String apiKey;

    public BigDecimal fetchPairConversionRate(String originalCurrency, String targetCurrency) {
        String urlString = apiUrl + "/" + apiKey + String.format(PAIR_CONVERSION, originalCurrency, targetCurrency);
        BigDecimal rate = BigDecimal.ONE;
        try{
            HttpClient client = HttpClient.newHttpClient();

            // Build the HttpRequest
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlString))
                    .GET()
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Check if the response is successful
            if (response.statusCode() == 200) {
                // Parse the JSON response
                JSONObject jsonResponse = new JSONObject(response.body());
                if (jsonResponse.getString("result").equals("success")) {
                    // Extract conversion_rate
                    double conversionRate = jsonResponse.getDouble("conversion_rate");
                    rate = BigDecimal.valueOf(conversionRate);
                } else {
                    log.error("Error: " + jsonResponse.getString("result"));
                }
            } else {
                log.error("GET request failed. Response Code: " + response.statusCode());
            }

        } catch (InterruptedException ie) { // InterruptedException is caught here
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
        }

        return rate;
    }
}
