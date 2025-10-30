package com.davidsafta.homework;

public class BrowserConfig {
    // ---- Câmpuri (private) ----
    private BrowserType browser;
    private String version;
    private boolean isHeadless;

    // ---- Constructor 1: primește toate 3 argumentele ----
    public BrowserConfig(BrowserType browser, String version, boolean isHeadless) {
        this.browser = browser;
        this.version = version;
        this.isHeadless = isHeadless;
    }

    // ---- Constructor 2: primește doar browser și version; isHeadless = false ----
    public BrowserConfig(BrowserType browser, String version) {
        this(browser, version, false);
    }

    // ---- Constructor 3: primește doar browser; version = "latest", isHeadless = false ----
    public BrowserConfig(BrowserType browser) {
        this(browser, "latest", false);
    }

    // ---- Metodă statică "factory" ----
    public static BrowserConfig createDefaultChromeConfig() {
        return new BrowserConfig(BrowserType.CHROME, "latest", true);
    }

    // ---- Metodă de afișare ----
    public void afiseazaConfig() {
        System.out.println(
                "Browser: " + browser +
                        ", Version: " + version +
                        ", Headless: " + isHeadless
        );
    }

    // (opțional) getters dacă vrei să le folosești mai târziu
    public BrowserType getBrowser() { return browser; }
    public String getVersion() { return version; }
    public boolean isHeadless() { return isHeadless; }
}
