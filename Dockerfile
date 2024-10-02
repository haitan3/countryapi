FROM openjdk:17-alpine

WORKDIR /app

COPY build/libs/countryapi-*.jar countryapi.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "countryapi.jar"]


#./gradlew build
#docker build -t countryapi .
#docker run -p 8080:8080 countryapi
