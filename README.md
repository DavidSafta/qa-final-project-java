[![CI Pipeline for QA Project](https://github.com/DavidSafta/qa-final-project-java/actions/workflows/ci.yml/badge.svg)](https://github.com/DavidSafta/qa-final-project-java/actions/workflows/ci.yml)

# qa-final-project-java

Proiect intermediar (Tema 3) – proiect Java/Maven containerizat, cu pipeline CI/CD (GitHub Actions) care:
- rulează `mvn test` (simulat pentru această temă),
- construiește imaginea Docker (Maven + JDK 17),
- **publică** imaginea în Docker Hub: `davidsafta/qa-final-project-java:latest`.

---

## Structură
config/app.yaml
data/.gitkeep
src/test/java/com/davidsafta/tests/ApiTest.txt (pseudocod Arrange–Act–Assert)
Dockerfile
pom.xml
.github/workflows/ci.yml

yaml
Copy code

## Config (YAML)
Fișier: `config/app.yaml`
```yaml
env: dev

service:
  baseUrl: https://jsonplaceholder.typicode.com

timeouts:
  connectMs: 5000
  readMs: 5000
Pseudotest API (Arrange–Act–Assert)
Fișier: src/test/java/com/davidsafta/tests/ApiTest.txt

Arrange: env=dev (din config/app.yaml), baseUrl + endpoint /todos/1, timeouts

Act: request HTTP GET către {baseUrl}{endpoint}

Assert: status 200 și răspunsul conține câmpul title nenul

Pentru această temă rulăm simulat mvn test (nu există cod Java de testare real).

Build & Run local (opțional)
bash
Copy code
docker pull davidsafta/qa-final-project-java:latest
docker run --rm davidsafta/qa-final-project-java:latest
CI/CD (GitHub Actions)
Workflow: .github/workflows/ci.yml

Joburi

test – rulează mvn -q test (simulat, trece instant)

build-and-push (needs: test) – construiește și publică în Docker Hub

Secrete necesare (Settings → Secrets and variables → Actions)

DOCKERHUB_USERNAME – user-ul de Docker Hub

DOCKERHUB_TOKEN – Personal Access Token (permisiuni Read/Write/Delete)

Tag publicat: davidsafta/qa-final-project-java:latest
Status CI/CD: vezi badge-ul de sus (trebuie să fie passing verde).

Notițe Docker
Proiectul include .dockerignore pentru builduri rapide și imagini mai mici (excludem fișiere de IDE, .git etc).

## Session 6 – Username Validator
Fișier: `src/main/java/com/davidsafta/homework/UsernameValidator.java`

Cum rulezi (IntelliJ):
Run ▶ pe metoda `main` din `UsernameValidator`. 
Programul cere username și afișează erori până când inputul respectă regulile; apoi confirmă “Username acceptat”.

locațiile fișierelor:

src/main/java/com/davidsafta/homework/BrowserType.java

src/main/java/com/davidsafta/homework/BrowserConfig.java

src/main/java/com/davidsafta/homework/TestConfigRunner.java

cum rulezi: Run → TestConfigRunner.main (afişează 4 linii cu configurațiile).

mențiune că enum + 3 constructori supraîncărcați + metoda statică “factory” + metoda de afișare sunt implementate.

## Session 8 – User repository & validation

Fișiere:
- `src/main/java/com/davidsafta/homework/User.java`
- `src/main/java/com/davidsafta/homework/AdminUser.java`
- `src/main/java/com/davidsafta/homework/UserRepository.java`
- `src/main/java/com/davidsafta/homework/InvalidUserDataException.java`
- `src/main/java/com/davidsafta/homework/Main.java`

Cum rulezi (IntelliJ):
- Rulezi metoda `main` din clasa `Main`.

Ce face:
- Adaugă un user valid și un admin valid în `UserRepository`.
- Încearcă să adauge un user cu username prea scurt → aruncă `InvalidUserDataException`.
- Încearcă să adauge un user cu vârstă negativă → aruncă `InvalidUserDataException`.
- La final afișează în consolă lista de utilizatori validați din repository.

## Session 9–10 – TestNG sanity + repository tests

### Fișiere test:
- `src/test/java/com/davidsafta/tests/SanityTest.java`
- `src/test/java/com/davidsafta/tests/UserRepositoryTest.java`
- `src/test/java/com/davidsafta/tests/AdminUserTest.java`

### Ce verifică:
- **SanityTest**: verificare simplă cu TestNG (assert 2+3=5).
- **UserRepositoryTest**:
  - `addValidUser_increasesSize` – după add, mărimea colecției crește.
  - `addUser_withTooShortUsername_throws` – aruncă `InvalidUserDataException`.
  - `addUser_withNegativeAge_throws` – aruncă `InvalidUserDataException`.
- **AdminUserTest**:
  - `adminUser_toString_containsPermissionLevel` – `toString()` conține „permissionLevel/full_access”.
  - `adminUser_addToRepository_isAccepted` – adăugarea în `UserRepository` reușește.

### Cum rulezi (IntelliJ):
- Click dreapta pe `test` folder → **Run 'All Tests'** (sau rulezi individual pe fiecare clasă).
- Toate testele trebuie să fie verzi.

### CI/CD
- GitHub Actions rulează „Run Maven tests (simulated)” + build imagine Docker.
- Badge-ul pentru ultimul run trebuie să fie verde în tabul **Actions**.

## Session 11–12 – PostValidator (OOP + TestNG)

Fișiere:
- `src/main/java/com/davidsafta/homework/PostValidator.java`
- `src/test/java/com/davidsafta/tests/PostValidatorTest.java`

Cerințe implementate:
- `validate(String title, String content)`:
  - aruncă `IllegalArgumentException` dacă:
    - `title` este null/gol sau are < 5 caractere (mesaj: include "Title must have at least 5")
    - `content` este null/gol sau are < 10 caractere (mesaj: include "Content must have at least 10")
- `isValid(String title, String content)`:
  - apelează `validate(...)` și nu aruncă excepții pentru input valid.

Teste (TestNG):
- `PostValidatorTest`:
  - `validPost_isAccepted` → input valid → `assertTrue(PostValidator.isValid(...))`
  - `titleTooShort_throws` → așteaptă `IllegalArgumentException` (regex: ".*Title.*least 5.*")
  - `contentEmpty_throws` → așteaptă `IllegalArgumentException` (regex: ".*Content.*")
  - `contentTooShort_throws` → așteaptă `IllegalArgumentException` (regex: ".*Content.*")

Cum rulezi (IntelliJ):
- Click dreapta pe folderul `test` → **Run 'All Tests'**.
- Toate testele trebuie să fie verzi.

CI/CD:
- GitHub Actions rulează `Run Maven tests (simulated)` + build imagine Docker.
- Badge-ul pentru ultimul run trebuie să fie verde în tabul **Actions**.

Maven (extras):
- `testng` versiunea `7.10.2`
- `maven-surefire-plugin` include pattern `**/*Test.java`

