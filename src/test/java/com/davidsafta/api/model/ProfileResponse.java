package com.davidsafta.api.model;

public class ProfileResponse {

    public String status;
    public String message;
    public ProfileUser user;

    public static class ProfileUser {
        public int id;
        public String first_name;
        public String last_name;
        public String username;
        public String email;
        public String signup_date;
        public String profile_pic;
    }
}
