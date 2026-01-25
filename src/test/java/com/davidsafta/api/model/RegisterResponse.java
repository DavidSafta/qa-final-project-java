package com.davidsafta.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterResponse {

    @JsonProperty("status")
    private String status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("api_key")
    private String apiKey;

    @JsonProperty("confirmation_token")
    private String confirmationToken;

    @JsonProperty("username")
    private String username;

    @JsonProperty("user_id")
    private int userId;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public String getUsername() {
        return username;
    }

    public int getUserId() {
        return userId;
    }
}