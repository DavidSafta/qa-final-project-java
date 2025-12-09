package com.davidsafta.core;

import com.codeborne.selenide.Configuration;
import io.cucumber.java.After;
import io.cucumber.java.Before;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class Hooks {

    @Before
    public void setUp() {
        closeWebDriver();

        Configuration.baseUrl = ConfigManager.baseUrl();
        Configuration.browser = ConfigManager.browser();
        Configuration.headless = ConfigManager.headless();
        Configuration.timeout = ConfigManager.timeout();

        Configuration.screenshots = true;
        Configuration.savePageSource = true;
        Configuration.browserSize = "1920x1080";
    }

    @After
    public void tearDown() {
        closeWebDriver();
    }
}
