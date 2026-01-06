FROM maven:3.9.6-eclipse-temurin-21

WORKDIR /app

COPY pom.xml ./
RUN mvn -q -e -DskipTests dependency:go-offline

COPY src ./src

CMD ["mvn", "spring-boot:run"]
