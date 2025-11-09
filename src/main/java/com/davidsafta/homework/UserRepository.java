package com.davidsafta.homework;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private List<User> users = new ArrayList<>();

    public void addUser(User user) throws InvalidUserDataException {
        if (user == null) {
            throw new InvalidUserDataException("User-ul este null.");
        }
        if (user.getUsername() == null || user.getUsername().length() < 3) {
            throw new InvalidUserDataException("Username-ul trebuie să aibă cel puțin 3 caractere.");
        }
        if (user.getAge() < 0) {
            throw new InvalidUserDataException("Vârsta nu poate fi negativă.");
        }

        users.add(user);
    }

    public List<User> getUsers() {
        return users;
    }
}
