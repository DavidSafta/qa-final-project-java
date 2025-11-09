package com.davidsafta.homework;

public class Main {
    public static void main(String[] args) {
        UserRepository repo = new UserRepository();

        // 1) User valid
        try {
            User u1 = new User("testuser", 25);
            repo.addUser(u1);
            System.out.println("Adăugat: " + u1);
        } catch (InvalidUserDataException e) {
            System.err.println("Eroare user valid: " + e.getMessage());
        }

        // 2) AdminUser valid
        try {
            AdminUser admin = new AdminUser("admin", 30, "full_access");
            repo.addUser(admin);
            System.out.println("Adăugat: " + admin);
        } catch (InvalidUserDataException e) {
            System.err.println("Eroare admin: " + e.getMessage());
        }

        // 3) User cu username prea scurt
        try {
            User shortName = new User("ab", 22);
            repo.addUser(shortName);
            System.out.println("Adăugat: " + shortName);
        } catch (InvalidUserDataException e) {
            System.err.println("Eroare username prea scurt: " + e.getMessage());
        }

        // 4) User cu vârstă negativă
        try {
            User negativeAge = new User("baduser", -5);
            repo.addUser(negativeAge);
            System.out.println("Adăugat: " + negativeAge);
        } catch (InvalidUserDataException e) {
            System.err.println("Eroare vârstă negativă: " + e.getMessage());
        }

        // La final: afișăm utilizatorii validați
        System.out.println("\n=== Utilizatori validați în repository ===");
        for (User user : repo.getUsers()) {
            System.out.println(user);
        }
    }
}
