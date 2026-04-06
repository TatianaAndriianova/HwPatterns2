package ru.netology;

import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;
import ru.netology.dto.LoginDto;
import ru.netology.dto.RegistrationDto;

import static io.restassured.RestAssured.given;
import static ru.netology.RequestSpecProvider.SPEC;

public class AuthTest {

    @Test
    void shouldLoginWithActiveUser() {
        RegistrationDto user = DataGenerator.getActiveUser();

        given()
                .spec(SPEC)
                .body(new LoginDto(user.getLogin(), user.getPassword()))
                .when()
                .post("/api/auth")
                .then()
                .statusCode(200);
    }

    @Test
    void shouldNotLoginWithBlockedUser() {
        RegistrationDto user = DataGenerator.getBlockedUser();

        given()
                .spec(SPEC)
                .body(new LoginDto(user.getLogin(), user.getPassword()))
                .when()
                .post("/api/auth")
                .then()
                .statusCode(400);
    }

    @Test
    void shouldNotLoginWithWrongPassword() {
        RegistrationDto user = DataGenerator.getActiveUser();

        given()
                .spec(SPEC)
                .body(new LoginDto(user.getLogin(), "wrongPassword123"))
                .when()
                .post("/api/auth")
                .then()
                .statusCode(400);
    }

    @Test
    void shouldNotLoginWithWrongLogin() {
        RegistrationDto user = DataGenerator.getActiveUser();

        given()
                .spec(SPEC)
                .body(new LoginDto("nonexistent_user_xyz", user.getPassword()))
                .when()
                .post("/api/auth")
                .then()
                .statusCode(400);
    }

    @Test
    void shouldNotLoginWithNonExistentUser() {
        given()
                .spec(SPEC)
                .body(new LoginDto("ghost_user_xyz", "somePassword1"))
                .when()
                .post("/api/auth")
                .then()
                .statusCode(400);
    }
}