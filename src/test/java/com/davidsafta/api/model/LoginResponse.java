package com.davidsafta.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponse {

    private String status;
    private String message;
    private String token;

    @JsonProperty("user")
    @JsonIgnoreProperties(ignoreUnknown = true)
    private User user;

    // --- nested User ---
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class User {
        private String id;
        private String username;
        private String email;

        public String getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

        public String getEmail() {
            return email;
        }
    }

    // --- getters standard pentru răspuns ---
    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }

    /**
     * Helper ca să poți folosi loginResponse.getUserId()
     * și să se potrivească cu userId de la RegisterResponse.
     */
    public int getUserId() {
        if (user == null || user.getId() == null) {
            return 0;
        }
        try {
            return Integer.parseInt(user.getId());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}