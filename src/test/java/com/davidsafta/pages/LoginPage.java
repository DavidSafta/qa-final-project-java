package com.davidsafta.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class LoginPage {

    // locatori toleranți ca să nu crape pe #email strict
    private final SelenideElement emailInput = $(
            "input#email, input[name='email'], input[type='email'], input[id*='email']"
    );

    private final SelenideElement passwordInput = $(
            "input#password, input[name='password'], input[type='password'], input[id*='pass']"
    );

    private final SelenideElement loginButton = $(
            "button[type='submit'], input[type='submit'], button[name*='login'], button[id*='login']"
    );

    public void openLogin() {
        // dacă la voi în proiect e alt path, schimbi DOAR asta
        open("/login_register.php");
    }

    public void shouldBeOpened() {
        emailInput.shouldBe(visible);
        passwordInput.shouldBe(visible);
    }

    public void login(String email, String password) {
        emailInput.setValue(email);
        passwordInput.setValue(password);
        loginButton.click();
    }
}
