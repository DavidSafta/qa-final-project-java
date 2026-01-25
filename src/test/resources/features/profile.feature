Feature: Profil

  Background:
    Given utilizatorul deschide pagina de login
    When utilizatorul se autentifica cu credențiale valide
    Then utilizatorul ajunge pe homepage

  Scenario: Actualizare profil cu Data Table
    When intru în profil și actualizez setările
      | bio | Bio actualizată din Cucumber |
    Then profilul este salvat
