package org.example.cedc.util;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author hamza mustafa khan
 * @mailto : hamzamkhan@outlook.com
 */

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
            URL url = new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Parse JSON response
                JSONObject jsonResponse = new JSONObject(response.toString());
                if (jsonResponse.getString("result").equals("success")) {
                    // Extract conversion_rate
                    double conversionRate = jsonResponse.getDouble("conversion_rate");
                    rate = BigDecimal.valueOf(conversionRate);
                } else {
                    System.out.println("Error: " + jsonResponse.getString("result"));
                }
            } else {
                System.out.println("GET request failed. Response Code: " + responseCode);
            }

        } catch (Exception e){
            e.printStackTrace();
        }


        return rate;
    }
}
