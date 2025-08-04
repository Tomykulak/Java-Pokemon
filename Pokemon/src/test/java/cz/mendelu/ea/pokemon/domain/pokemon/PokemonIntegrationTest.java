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
import org.springframework.http.HttpStatus;
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

    @Autowired
    private PokemonService pokemonService;

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
                            "name": "Tomy",
                            "type1": "Electric",
                            "hp": 50,
                            "attack": 55,
                            "defense": 40,
                            "sp_attack": 50,
                            "sp_defense": 50,
                            "speed": 90,
                            "generation": 1,
                            "legendary": false
                        }
                        """)
                .when()
                .post("/pokemons")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("content.name", is("Tomy"));
    }

    // if keycloack works
    /*
    @Test
    public void testCreatePokemonInvalidPermission() {
        given()
                .contentType("application/json")
                .body("""
                        {
                            "id": 100,
                            "name": "Tomy",
                            "type1": "Electric",
                            "hp": 50,
                            "attack": 55,
                            "defense": 40,
                            "sp_attack": 50,
                            "sp_defense": 50,
                            "speed": 90,
                            "generation": 1,
                            "legendary": false
                        }
                        """)
                .when()
                .post("/pokemons")
                .then()
                .statusCode(401);
    }
    */

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
                .statusCode(400);  // Assuming 400 for bad request
    }

    @Test
    public void testUpdatePokemon() {
        given()
                .contentType("application/json")
                .accept("application/json")
                .body("""
                        {
                            "name": "updatedPokemon",
                            "type1": "Fire",
                            "hp": 78,
                            "attack": 84,
                            "defense": 78,
                            "sp_attack": 109,
                            "sp_defense": 85,
                            "speed": 100,
                            "generation": 1,
                            "legendary": false
                        }
                        """)
                .when()
                .put("/pokemons/{id}", 1)
                .then()
                .statusCode(HttpStatus.ACCEPTED.value())
                .body("content.name", equalTo("updatedPokemon"));
    }

    @Test
    public void testDeletePokemon() {
        given()
                .when()
                .delete("/pokemons/{id}", 1)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
