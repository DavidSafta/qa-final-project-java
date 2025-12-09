Feature: Postari

  Background:
    Given utilizatorul deschide pagina de login
    When utilizatorul se autentifica cu credențiale valide
    Then utilizatorul ajunge pe homepage

  Scenario Outline: Creează o postare
    When creează o postare cu textul "<text>"
    Then postarea apare în feed

    Examples:
      | text                  |
      | Salut din Cucumber!   |
      | A doua postare        |
