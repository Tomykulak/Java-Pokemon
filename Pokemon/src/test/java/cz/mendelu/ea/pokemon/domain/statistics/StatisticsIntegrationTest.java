package cz.mendelu.ea.pokemon.domain.statistics;

import cz.mendelu.ea.pokemon.domain.pokemon.Pokemon;
import cz.mendelu.ea.pokemon.domain.pokemon.PokemonService;
import cz.mendelu.ea.pokemon.utils.AuthHelper;
import cz.mendelu.ea.pokemon.utils.data.DataImporter;
import cz.mendelu.ea.pokemon.utils.response.ObjectResponse;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/test-data/cleanup.sql")
@Sql("/test-data/base-data.sql")
public class StatisticsIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private AuthHelper authHelper;

    @MockBean
    private DataImporter dataImporter; // Mock the DataImporter

    @Mock
    private StatisticsService statisticsService;

    @Mock
    private PokemonService pokemonService;

    @InjectMocks
    private StatisticsController statisticsController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    public void testGetTrainerStatistics() {
        TrainerStatistics trainerStatistics = new TrainerStatistics();
        trainerStatistics.setCalculateAverageWinRate("70,00%");

        when(statisticsService.getTrainerStatistics()).thenReturn(trainerStatistics);

        given()
                .when()
                .get("/statistics/trainer")
                .then()
                .statusCode(200)
                .body("version", is(1))
                .body("content.calculateAverageWinRate", is("70,00%"));
    }


}