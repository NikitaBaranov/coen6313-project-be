package com.NikitaBaranov.coen6313projectbe.client;

import com.NikitaBaranov.coen6313projectbe.domain.dto.RecognitionResult;
import com.NikitaBaranov.coen6313projectbe.domain.dto.RecognitionResults;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

@Component
public class MlEngineClient {
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static final ObjectMapper mapper = new ObjectMapper();

    @Value("${CLIENT_SECRET}")
    private String clientSecret;

    private static String getAccessToken(String url, Map<String, String> headers, String body) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();

        for (Map.Entry<String, String> header : headers.entrySet()) {
            connection.setRequestProperty(header.getKey(), header.getValue());
        }
        String method = body.isEmpty() ? "GET" : "POST";
        print(method + " to " + url);
        connection.setRequestMethod(method);
        if (method.equals("POST")) {
            connection.setDoOutput(true);
            connection.setRequestProperty("Accept-Charset", UTF8.name());
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + UTF8.name());
            final String formBodyString = body;
            print("form body:");
            print(formBodyString);
            byte[] formBody = formBodyString.getBytes(UTF8);
            connection.setRequestProperty("Content-Length", Integer.toString(formBody.length));
            new DataOutputStream(connection.getOutputStream()).write(formBody);
        }
        int responseCode = connection.getResponseCode();
        if (responseCode >= 400) {
            System.err.println("request failed with response code " + responseCode);
            String errorLine;
            final InputStream errorStream = connection.getErrorStream();
            if (errorStream != null) {
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
                while ((errorLine = errorReader.readLine()) != null) {
                    System.err.println(errorLine);
                }
            }
            System.exit(3);
        }
        final InputStream inputStream = connection.getInputStream();
        if (inputStream == null) {
            return "";
        }
        BufferedReader responseReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = responseReader.readLine()) != null) {
            sb.append(line.trim());
        }
        return sb.toString();
    }

    private static void print(String message) {
        System.out.println(message);
    }

    public RecognitionResult getPrediction(Double[] image) throws IOException {

        // Getting token
        String url = "https://accounts.google.com/o/oauth2/token";
        Map<String, String> headers = new HashMap<>();
        String call = getAccessToken(url, headers, clientSecret);
        String token = mapper.readTree(call).get("access_token").asText();

        // Compose recognition request
        ArrayNode img = mapper.createArrayNode();
        for (Double d : image) {
            img.add(d);
        }

        ObjectNode jNode = mapper.createObjectNode();
        jNode.put("instances", img);

        RestTemplate restTemplate = new RestTemplate();

        UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("ml.googleapis.com")
                .path("/v1/projects/synthetic-eon-167418/models/MNIST/versions/v1:predict")
                .build()
                .encode();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));
        httpHeaders.set("Authorization", "Bearer " + token);
        HttpEntity requestEntity = new HttpEntity<>(jNode, httpHeaders);

        // Send Recognition Request
        RecognitionResults recognitionResults = restTemplate.postForObject(uri.toUri(), requestEntity, RecognitionResults.class);

        // Due tp a API ML Engine always return an array, so we need only first element
        return recognitionResults.getPredictions()[0];
    }
}
