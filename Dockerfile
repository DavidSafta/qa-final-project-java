FROM maven:3.9-eclipse-temurin-17
WORKDIR /app

# pregătim cache pentru dependențe (nu avem, dar e bună practica)
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline

# copiem restul proiectului (config, test plan etc.)
COPY . .

# pentru tema aceasta doar simulăm rularea testelor
CMD ["mvn","-q","test"]