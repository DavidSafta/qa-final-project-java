package com.davidsafta.homework;
import java.util.Scanner;
public class UsernameValidator {
    // Reguli:
    // - Lungime: 6..12 (inclusiv)
    // - Fara spatii
    // - Doar litere si cifre
    // - Trebuie sa contina cel putin o cifra

    public static boolean hasValidLength(String s) {
        return s != null && s.length() >= 6 && s.length() <= 12;
    }

    public static boolean hasNoSpaces(String s) {
        return s != null && !s.contains(" ");
    }

    public static boolean onlyLettersAndDigits(String s) {
        if (s == null) return false;
        for (char c : s.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) return false;
        }
        return true;
    }

    public static boolean containsAtLeastOneDigit(String s) {
        if (s == null) return false;
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String username;

        do {
            System.out.print("Introdu un username: ");
            username = scanner.nextLine();

            boolean ok = true;

            // 1) lungime
            if (!hasValidLength(username)) {
                System.out.println("Eroare: Username-ul trebuie sa aiba intre 6 si 12 caractere.");
                ok = false;
            }

            // 2) fara spatii
            if (!hasNoSpaces(username)) {
                System.out.println("Eroare: Username-ul nu poate contine spatii.");
                ok = false;
            }

            // 3) doar litere si cifre
            if (!onlyLettersAndDigits(username)) {
                System.out.println("Eroare: Username-ul poate contine doar litere si cifre.");
                ok = false;
            }

            // 4) cel putin o cifra
            if (!containsAtLeastOneDigit(username)) {
                System.out.println("Eroare: Username-ul trebuie sa contina cel putin o cifra.");
                ok = false;
            }

            if (ok) {
                System.out.println("Username acceptat: " + username);
                break; // iesim din do-while cand e valid
            }

        } while (true);

        scanner.close();
    }
}
