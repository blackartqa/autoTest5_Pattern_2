package ru.netology.data;


import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Data;

import java.util.Locale;

import static io.restassured.RestAssured.given;

@Data
public class DataGenerator {
    private DataGenerator(){}

    public static class Registration{
        private Registration(){}

        public static RegistrationData activeUserDataGenerate(){
            Faker faker = new Faker(new Locale("en"));
            return new RegistrationData(
                    faker.name().username(),
                    faker.internet().password(),
                    "active"
            );
        }

        public static RegistrationData blockedUserDataGenerate(){
            Faker faker = new Faker(new Locale("en"));
            return new RegistrationData(
                    faker.name().username(),
                    faker.internet().password(),
                    "blocked"
            );
        }

        private static final RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(9999)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        public static RegistrationData activeUserRegistration(){
            RegistrationData registrationData = activeUserDataGenerate();
            Gson gsonBuilder = new GsonBuilder().create();
            String jsonRegistrationData = gsonBuilder.toJson(registrationData);
            given()
                    .spec(requestSpec)
                    .body(jsonRegistrationData)
                    .when()
                    .post("/api/system/users")
                    .then()
                    .statusCode(200);
            return registrationData;
        }

        public static RegistrationData blockedUserRegistration(){
            RegistrationData registrationData = blockedUserDataGenerate();
            Gson gsonBuilder = new GsonBuilder().create();
            String jsonRegistartionData = gsonBuilder.toJson(registrationData);
            given()
                    .spec(requestSpec)
                    .body(jsonRegistartionData)
                    .when()
                    .post("/api/system/users")
                    .then()
                    .statusCode(200);
            return registrationData;
        }

        public static String notCorrectLogin() {
            Faker faker = new Faker(new Locale("en"));
            return faker.name().username();
        }

        public static String notCorrectPassword() {
            Faker faker = new Faker(new Locale("en"));
            return faker.internet().password();
        }


    }
}
