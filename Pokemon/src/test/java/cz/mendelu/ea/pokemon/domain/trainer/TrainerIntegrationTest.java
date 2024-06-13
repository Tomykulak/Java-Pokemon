package cz.mendelu.ea.pokemon.domain.trainer;

import cz.mendelu.ea.pokemon.PokemonApplication;
import cz.mendelu.ea.pokemon.domain.arena.Arena;
import cz.mendelu.ea.pokemon.domain.arena.ArenaService;
import cz.mendelu.ea.pokemon.utils.AuthHelper;
import cz.mendelu.ea.pokemon.utils.data.DataImporter;
import cz.mendelu.ea.pokemon.utils.exceptions.NotFoundException;
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
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PokemonApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = "/test-data/cleanup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/test-data/base-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class TrainerIntegrationTest {

    @LocalServerPort
    private int port;

    @MockBean
    private ArenaService arenaService;

    @MockBean
    private TrainerService trainerService;

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

        // Mocking the getTrainerById method
        when(trainerService.findById(1L)).thenReturn(new Trainer(1L, "Ash Ketchum", 10, 20, 5, new Arena()));
        when(trainerService.findById(-1L)).thenThrow(new NotFoundException());
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
    public void testGetTrainerById() {
        given()
                .when()
                .get("/trainers/{id}", 1)
                .then()
                .statusCode(200)
                .body("content.name", is("Ash Ketchum"));
    }

    @Test
    public void testGetInvalidTrainerId() {
        given()
                .when()
                .get("/trainers/{id}", -1)
                .then()
                .statusCode(404);  // Expecting 404 for not found
    }
}
