package ru.netology.data;

import com.github.javafaker.Faker;
import ru.netology.dto.RegistrationDto;

import static io.restassured.RestAssured.given;
import static ru.netology.RequestSpecProvider.SPEC;

public class DataGenerator {

    private static final Faker faker = new Faker();

    private DataGenerator() {
    }

    public static RegistrationDto createUser(String status) {
        RegistrationDto user = new RegistrationDto(
                faker.name().username(),
                faker.internet().password(8, 16),
                status
        );
        given()
                .spec(SPEC)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
        return user;
    }

    public static RegistrationDto getActiveUser() {
        return createUser("active");
    }

    public static RegistrationDto getBlockedUser() {
        return createUser("blocked");
    }
}
