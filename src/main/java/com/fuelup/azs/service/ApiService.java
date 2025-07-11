package com.fuelup.azs.service;

import com.fuelup.azs.config.ConfigLoader;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ApiService {
    
    private static final Logger logger = LoggerFactory.getLogger(ApiService.class);
    
    private final String baseUrl;
    private final String apiKey;
    
    public ApiService() {
        this.baseUrl = ConfigLoader.getApiBaseUrl();
        this.apiKey = ConfigLoader.getApiKey();
    }
    
    public String getStationsData() throws IOException {
        String url = baseUrl + "/station?apikey=" + apiKey;
        return makeRequest(url);
    }
    
    public String getPricesData() throws IOException {
        String url = baseUrl + "/price?apikey=" + apiKey;
        return makeRequest(url);
    }
    
    private String makeRequest(String url) throws IOException {
        logger.debug("Выполняется запрос к API: {}", url);
        
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            request.setHeader("Accept", "application/json");
            
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getCode();
                String responseBody = EntityUtils.toString(response.getEntity());
                
                if (statusCode == 200) {
                    logger.debug("Успешный ответ от API. Размер ответа: {} символов", responseBody.length());
                    return responseBody;
                } else {
                    logger.error("Ошибка API. Код ответа: {}, Тело ответа: {}", statusCode, responseBody);
                    throw new IOException("API вернул код ошибки: " + statusCode);
                }
            }
        } catch (Exception e) {
            logger.error("Ошибка при выполнении запроса к API: {}", url, e);
            throw new IOException("Ошибка при выполнении запроса к API", e);
        }
    }
}