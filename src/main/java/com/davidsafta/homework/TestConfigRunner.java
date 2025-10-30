package com.davidsafta.homework;

public class TestConfigRunner {
    public static void main(String[] args) {
        // 1) Constructorul cu toate cele 3 argumente
        BrowserConfig cfg1 = new BrowserConfig(BrowserType.CHROME, "120.0", true);
        cfg1.afiseazaConfig();

        // 2) Constructorul cu browser + version (isHeadless = false)
        BrowserConfig cfg2 = new BrowserConfig(BrowserType.FIREFOX, "118.0");
        cfg2.afiseazaConfig();

        // 3) Constructorul doar cu browser (version = "latest", isHeadless = false)
        BrowserConfig cfg3 = new BrowserConfig(BrowserType.EDGE);
        cfg3.afiseazaConfig();

        // 4) Metoda statică "factory" pentru Chrome default (latest, headless = true)
        BrowserConfig cfg4 = BrowserConfig.createDefaultChromeConfig();
        cfg4.afiseazaConfig();
    }
}
