package com.reno.reno.service.supabase;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reno.reno.constant.ExternalServiceConstant;
import com.reno.reno.model.common.ApiError;
import com.reno.reno.model.exception.ApiException;
import com.reno.reno.model.service.SignUpRequest;
import com.reno.reno.model.service.signup.SignUpResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Profile("!regression")
public class SupabaseServiceImpl implements SupabaseService {

    @NotNull
    @Value("${api.supabase.url}")
    String supabaseApi;

    @Value("${api.supabase.anon_key}")
    String supabaseAnonKey;

    @Override
    public SignUpResponse postSignUp(SignUpRequest request) throws Exception {
        String url = new String(assertApiURLOrElseThrow(supabaseApi, "/functions/v1/sign-up-email"));
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<SignUpRequest> body = convertToBody(request, supabaseAnonKey);
        SignUpResponse response = new SignUpResponse();
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, body, SignUpResponse.class).getBody();
        } catch (HttpStatusCodeException e) {
            handelError(e);
        } catch (RestClientException e) {
            logClientException(e);
            throw e;
        }
        return response;
    }

    private String assertApiURLOrElseThrow(String url, String endpointUrl) throws Exception {
        StringBuilder actualUrl = new StringBuilder();
        if (url == null) {
            throw new Exception("External URL is null");
        }
        actualUrl.append(url);
        actualUrl.append(endpointUrl);
        return actualUrl.toString();
    }

    private <T> HttpEntity<T> convertToBody(T request, String apiKey) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(ExternalServiceConstant.HEADER_CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(ExternalServiceConstant.HEADER_AUTHORIZATION, "Bearer " + apiKey);
        HttpEntity<T> body = new HttpEntity<>(request, headers);
        return body;
    }

    private void handelError(HttpStatusCodeException e) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ApiError error;
        try {
            error = mapper.readValue(e.getResponseBodyAsString(), ApiError.class);
        } catch (IOException ex) {
            throw new Exception(ex.getMessage());
        }
        if (e.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
            throw new Exception(error.getErrorMessage());
        } else if (e.getStatusCode().equals(HttpStatus.TOO_MANY_REQUESTS)) {
            throw new Exception(error.getErrorMessage());
        } else {
            throw new ApiException(error.getStatusCode(), error.getErrorMessage());
        }
    }

    private void logClientException(RestClientException e) {
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));
        log.error(errors.toString());
    }
}