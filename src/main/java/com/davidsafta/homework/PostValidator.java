package com.davidsafta.homework;

public class PostValidator {

    /** Aruncă IllegalArgumentException dacă datele sunt invalide. */
    public static void validate(String title, String content) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Content cannot be empty");
        }
        String t = title.trim();
        String c = content.trim();

        if (t.length() < 5) {
            throw new IllegalArgumentException("Title must have at least 5 characters");
        }
        if (c.length() < 10) {
            throw new IllegalArgumentException("Content must have at least 10 characters");
        }
    }

    /** Conveniență pentru teste/folosire rapidă. */
    public static boolean isValid(String title, String content) {
        validate(title, content);
        return true;
    }
}
