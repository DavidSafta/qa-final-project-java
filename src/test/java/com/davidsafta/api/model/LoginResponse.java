package com.davidsafta.api.model;

public class LoginResponse {

    public String status;
    public String message;
    public String token;
    public LoginUser user;

    public static class LoginUser {
        public int id;
        public String username;
        public String email;
    }
}
