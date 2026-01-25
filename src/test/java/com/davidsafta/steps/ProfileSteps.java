package com.davidsafta.steps;

import com.davidsafta.pages.ProfilePage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Map;

public class ProfileSteps {

    private final ProfilePage profilePage = new ProfilePage();
    private String lastBio;

    @When("intru în profil și actualizez setările")
    public void intruInProfilSiActualizezSetarile(DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);

        if (data.containsKey("bio")) {
            lastBio = data.get("bio");
            profilePage.updateBio(lastBio);
        }
    }

    @Then("profilul este salvat")
    public void profilulEsteSalvat() {
        if (lastBio != null) {
            profilePage.shouldSeeBio(lastBio);
        }
    }
}
