package com.davidsafta.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

    private static final Properties props = new Properties();

    static {
        try (InputStream is = ConfigManager.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (is != null) {
                props.load(is);
            }
        } catch (IOException e) {
            throw new RuntimeException("Nu pot citi config.properties", e);
        }
    }

    private static String fromSystemOrProps(String key) {
        String sys = System.getProperty(key);
        if (sys != null && !sys.isBlank()) return sys.trim();

        String val = props.getProperty(key);
        if (val != null && !val.isBlank()) return val.trim();

        return null;
    }

    public static String get(String key) {
        String val = fromSystemOrProps(key);
        if (val == null) {
            throw new RuntimeException("Lipsește cheia din config.properties: " + key);
        }
        return val;
    }

    public static String get(String key, String defaultValue) {
        String val = fromSystemOrProps(key);
        return (val == null) ? defaultValue : val;
    }

    private static String getFirst(String... keys) {
        for (String k : keys) {
            String val = fromSystemOrProps(k);
            if (val != null) return val;
        }
        throw new RuntimeException("Lipsește cheia din config.properties: " + String.join(" / ", keys));
    }

    // ------- settings Selenide -------
    public static String baseUrl() {
        return get("baseUrl", "https://test.hapifyme.com");
    }

    public static String browser() {
        return get("browser", "chrome");
    }

    public static boolean headless() {
        return Boolean.parseBoolean(get("headless", "false"));
    }

    public static long timeout() {
        return Long.parseLong(get("timeout", "8000"));
    }

    // ------- credențiale -------
    // Acceptă și variantele vechi ca să nu mai crape dacă ai uitat ceva
    public static String email() {
        return getFirst("email", "validEmail");
    }

    public static String password() {
        return getFirst("password", "validPassword");
    }
}
