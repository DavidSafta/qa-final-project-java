Feature: Login

  Scenario: Login pozitiv cu credențiale valide
    Given utilizatorul deschide pagina de login
    When utilizatorul se autentifica cu credențiale valide
    Then utilizatorul ajunge pe homepage
