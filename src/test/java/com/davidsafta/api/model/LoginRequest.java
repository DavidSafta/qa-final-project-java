package com.davidsafta.api.model;

public class LoginRequest {

    private String email;
    private String password;
    private String apiKey;

    public LoginRequest(String email, String password, String apiKey) {
        this.email = email;
        this.password = password;
        this.apiKey = apiKey;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getApiKey() {
        return apiKey;
    }
}