package com.davidsafta.core;

import com.codeborne.selenide.Configuration;
import io.cucumber.java.Before;

public class TestConfig {
    @Before("@ui")
    public void selenideSetUp() {
        // baseUrl pentru UI (NU /api)
        Configuration.baseUrl = "https://test.hapifyme.com";
        Configuration.browserSize = "1366x768";
        Configuration.timeout = 8000;
        // Dacă vrei fără fereastră: Configuration.headless = true;
    }
}
