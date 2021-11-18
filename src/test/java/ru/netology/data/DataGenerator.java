package ru.netology.data;


import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private DataGenerator() {
    }

    public static class Registration {
        private Registration() {
        }

        public static RegistrationData generateUser(String status) {
            return new RegistrationData(generateRandomLogin(), generateRandomPassword(), status);
        }

        private static RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(9999)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        public static RegistrationData userRegistration(String status) {
            RegistrationData registrationData = generateUser(status);
            given()
                    .spec(requestSpec)
                    .body(registrationData)
                    .when()
                    .post("/api/system/users")
                    .then()
                    .statusCode(200);
            return registrationData;
        }


        public static String generateRandomLogin() {
            Faker faker = new Faker(new Locale("en"));
            return faker.name().username();
        }

        public static String generateRandomPassword() {
            Faker faker = new Faker(new Locale("en"));
            return faker.internet().password();
        }


    }
}
