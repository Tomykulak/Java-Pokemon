package cz.mendelu.ea.pokemon.domain.statistics;


import cz.mendelu.ea.pokemon.domain.pokemon.PokemonService;
import cz.mendelu.ea.pokemon.utils.response.ObjectResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
@Validated
@Tag(
        name = "Statistics",
        description = "Statistics endpoint."
)
public class StatisticsController {
    private final StatisticsService statisticsService;
    private final PokemonService pokemonService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService, PokemonService pokemonService) {
        this.statisticsService = statisticsService;
        this.pokemonService = pokemonService;
    }

    @GetMapping(value = "", produces = "application/json")
    @Valid
    @Operation(
            summary = "returns all entity counts trainers/pokemons/arenas.",
            description = "returns all entity counts trainers/pokemons/arenas."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "returns all entity counts trainers/pokemons/arenas.")
    })
    public ObjectResponse<Statistics> getStatistics() {
        var statistics = statisticsService.getStatistics();
        return new ObjectResponse<>(statistics, 1);
    }

    @GetMapping(value = "/pokemon", produces = "application/json")
    @Valid
    @Operation(
            summary = "returns pokemons statistics.",
            description = "returns pokemons statistics."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "returns pokemons statistics.")
    })
    public ObjectResponse<PokemonStatistics> getPokemonStatistics() {
        var pokemonStatistics = statisticsService.getPokemonStatistics();
        return new ObjectResponse<>(pokemonStatistics, 1);
    }

    @GetMapping(value = "trainer", produces = "application/json")
    @Valid
    @Operation(
            summary = "returns trainer statistics.",
            description = "returns trainer statistics."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "returns trainer statistics.")
    })
    public ObjectResponse<TrainerStatistics> getTrainerStatistics(){
        var trainerStatistics = statisticsService.getTrainerStatistics();
        return new ObjectResponse<>(trainerStatistics, 1);
    }

}
