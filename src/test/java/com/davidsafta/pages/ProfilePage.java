package com.davidsafta.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class ProfilePage {

    private final SelenideElement bioInput =
            $("textarea[name='bio'], textarea#bio, textarea[id*='bio'], textarea");

    private final SelenideElement saveButton =
            $("button[type='submit'], input[type='submit'], button[name*='save'], button#save");

    public void openProfile() {
        open("/profile.php");
    }

    public void shouldBeOpened() {
        bioInput.shouldBe(visible);
    }

    public void updateBio(String bio) {
        bioInput.shouldBe(visible).clear();
        bioInput.setValue(bio);
    }

    public void save() {
        saveButton.shouldBe(enabled).click();
    }

    public void shouldSeeBio(String expectedBio) {
        bioInput.shouldBe(visible).shouldHave(value(expectedBio));
    }
}
