FROM openjdk:17-alpine

WORKDIR /app

COPY . .

RUN chmod +x ./gradlew

RUN ./gradlew clean build

RUN cp build/libs/countryapi-0.0.1-SNAPSHOT.jar countryapi.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "countryapi.jar"]