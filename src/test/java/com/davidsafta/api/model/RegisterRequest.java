package com.davidsafta.api.model;

public class RegisterRequest {

    public String first_name;
    public String last_name;
    public String email;
    public String password;

    public RegisterRequest(String firstName,
                           String lastName,
                           String email,
                           String password) {
        this.first_name = firstName;
        this.last_name = lastName;
        this.email = email;
        this.password = password;
    }
}
