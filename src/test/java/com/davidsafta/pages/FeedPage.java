package com.davidsafta.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class FeedPage {

    private final SelenideElement postInput = $(
            "textarea[name='post'], textarea[name='content'], textarea[placeholder*='Post'], textarea[placeholder*='post'], textarea"
    );

    private final SelenideElement postButton = $(
            "button[type='submit'], input[type='submit'], button[name*='post']"
    );

    public void openFeed() {
        open("/feed.php");
    }

    public void shouldBeOpened() {
        $("body").shouldBe(visible);
    }

    public void createPost(String text) {
        postInput.shouldBe(visible).setValue(text);
        postButton.shouldBe(enabled).click();
    }

    public void shouldSeePost(String text) {
        $$("body *").findBy(text(text)).shouldBe(visible);
    }
}
