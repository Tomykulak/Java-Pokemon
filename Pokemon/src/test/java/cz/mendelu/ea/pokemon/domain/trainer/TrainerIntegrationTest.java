package cz.mendelu.ea.pokemon.domain.trainer;

import cz.mendelu.ea.pokemon.utils.AuthHelper;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/test-data/cleanup.sql")
@Sql("/test-data/base-data.sql")
public class TrainerIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private AuthHelper authHelper;

    @BeforeEach
    public void configureRestAssured() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    public void testGetTrainers() {
        given()
                .when()
                .get("/trainers")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetTrainerById(){
        given()
                .when()
                .get("/trainers/{id}", 1)
                .then()
                .statusCode(200)
                .body("content.name", is("Ash Ketchum"));
    }

    @Test
    public void testGetInvalidTrainerId(){
        given()
                .when()
                .get("/trainers/{id}", -1)
                .then()
                .statusCode(500);
    }

}
