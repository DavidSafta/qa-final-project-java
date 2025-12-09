package com.davidsafta.steps;

import com.davidsafta.core.ConfigManager;
import com.davidsafta.pages.FeedPage;
import com.davidsafta.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps {

    private final LoginPage loginPage = new LoginPage();
    private final FeedPage feedPage = new FeedPage();

    @Given("utilizatorul deschide pagina de login")
    public void utilizatorulDeschidePaginaDeLogin() {
        loginPage.openLogin();
        loginPage.shouldBeOpened();
    }

    @When("utilizatorul se autentifica cu credențiale valide")
    public void utilizatorulSeAutentificaCuCredentialeValide() {
        loginPage.login(ConfigManager.email(), ConfigManager.password());
    }

    @Then("utilizatorul ajunge pe homepage")
    public void utilizatorulAjungePeHomepage() {
        feedPage.shouldBeOpened();
    }
}
