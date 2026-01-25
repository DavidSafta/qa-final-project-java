package com.davidsafta.steps;

import com.davidsafta.pages.FeedPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PostSteps {

    private final FeedPage feedPage = new FeedPage();
    private String lastPost;

    @When("creează o postare cu textul {string}")
    public void creeazaOPostareCuTextul(String text) {
        lastPost = text;
        feedPage.createPost(text);
    }

    @Then("postarea apare în feed")
    public void postareaApareInFeed() {
        feedPage.shouldSeePost(lastPost);
    }
}
