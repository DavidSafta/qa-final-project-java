package com.davidsafta.homework;

public class AdminUser extends User {
    private String permissionLevel;

    public AdminUser(String username, int age, String permissionLevel) {
        super(username, age);      // apelăm constructorul din User
        this.permissionLevel = permissionLevel;
    }

    public String getPermissionLevel() {
        return permissionLevel;
    }

    @Override
    public String toString() {
        return "AdminUser{" +
                "username='" + getUsername() + '\'' +
                ", age=" + getAge() +
                ", permissionLevel='" + permissionLevel + '\'' +
                '}';
    }
}
