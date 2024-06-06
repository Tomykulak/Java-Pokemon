package cz.mendelu.ea.pokemon.domain.pokemon;

import cz.mendelu.ea.pokemon.utils.AuthHelper;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/test-data/cleanup.sql")
@Sql("/test-data/base-data.sql")
public class PokemonIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private AuthHelper auth;

    @BeforeEach
    public void configureRestAssured(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    public void testGetPokemonById(){
        given()
            .when()
                .get("/pokemons/{id}", 1)
            .then()
                .statusCode(200)
                .body("content.name", is("Pikachu"));
    }
    @Test
    public void testGetInvalidPokemonId(){
        given()
                .when()
                    .get("/pokemons/{id}", -1)
                .then()
                    .statusCode(404);
    }

    @Test
    public void testCreatePokemon() {
        //var user = auth.login("admin");

        given()
                //.auth().oauth2(user)
                .contentType("application/json")
                .body("""
                        {
                        "id": 100,
                        "name": "Tomy"
                         }
                        """)
                .when()
                .post("/pokemons")
                .then()
                .statusCode(401);
                //.body("content.name", is("Tomy"))
                //.extract().path("id");
    }

    @Test
    public void testCreatePokemonInvalidPermission() {

        given()
                .contentType("application/json")
                .body("""
                        {
                        "id": 100,
                        "name": "Tomy"
                         }
                        """)
                .when()
                .post("/pokemons")
                .then()
                .statusCode(401);
    }


    @Test
    public void testCreateInvalidPokemon() {
        //var user = auth.login("admin");

        given()
                //.auth().oauth2(user)
                .contentType("application/json")
                .body("""
                        {
                            "name":""
                        }
                        """)
                .when()
                .post("/pokemons")
                .then()
                .statusCode(401);
    }

    @Test
    public void testUpdatePokemon() {
        given()
                .contentType("application/json")
                .accept("application/json")
                .body("""
                        {
                            "id": 1,
                            "name":"updatedPokemon"
                        }
                        """)
                .when()
                .put("/pokemons/{id}", 1)
                .then()
                .statusCode(HttpStatus.ACCEPTED.value())
                .body("content.name", equalTo("updatedPokemon"));
    }
}
