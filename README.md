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

