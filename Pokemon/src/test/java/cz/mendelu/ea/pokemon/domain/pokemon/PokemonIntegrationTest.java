package cz.mendelu.ea.pokemon.domain.pokemon;

import cz.mendelu.ea.pokemon.PokemonApplication;
import cz.mendelu.ea.pokemon.domain.arena.Arena;
import cz.mendelu.ea.pokemon.domain.arena.ArenaService;
import cz.mendelu.ea.pokemon.utils.AuthHelper;
import cz.mendelu.ea.pokemon.utils.data.DataImporter;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PokemonApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = "/test-data/cleanup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/test-data/base-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class PokemonIntegrationTest {
    @LocalServerPort
    private int port;

    @MockBean
    private ArenaService arenaService;

    @MockBean
    private DataImporter dataImporter;

    @Autowired
    private AuthHelper authHelper;

    @BeforeEach
    public void configureRestAssured() {
        MockitoAnnotations.openMocks(this);
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        // Mocking the getArenaById method
        when(arenaService.getArenaById(anyLong())).thenReturn(new Arena());

        // Mocking the importTrainers method in DataImporter
        doNothing().when(dataImporter).importTrainers();
        doNothing().when(dataImporter).importPokemons();
        doNothing().when(dataImporter).importArenas();
    }

    @Test
    public void testGetPokemons() {
        given()
                .when()
                .get("/pokemons")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetPokemonById() {
        given()
                .when()
                .get("/pokemons/{id}", 1)
                .then()
                .statusCode(200)
                .body("content.name", is("Pikachu"));
    }

    @Test
    public void testGetInvalidPokemonId() {
        given()
                .when()
                .get("/pokemons/{id}", -1)
                .then()
                .statusCode(404);
    }

    @Test
    public void testCreatePokemon() {
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
        given()
                .contentType("application/json")
                .body("""
                        {
                            "name": ""
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
                            "name": "updatedPokemon"
                        }
                        """)
                .when()
                .put("/pokemons/{id}", 1)
                .then()
                .statusCode(202)
                .body("content.name", equalTo("updatedPokemon"));
    }
}
