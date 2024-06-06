package cz.mendelu.ea.pokemon.domain.statistics;


import cz.mendelu.ea.pokemon.domain.pokemon.PokemonService;
import cz.mendelu.ea.pokemon.utils.response.ObjectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
@Validated
public class StatisticsController {
    private final StatisticsService statisticsService;
    private final PokemonService pokemonService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService, PokemonService pokemonService) {
        this.statisticsService = statisticsService;
        this.pokemonService = pokemonService;
    }

    @GetMapping(value = "", produces = "application/json")
    public ObjectResponse<Statistics> getStatistics() {
        var statistics = statisticsService.getStatistics();
        return new ObjectResponse<>(statistics, 1);
    }

    @GetMapping(value = "/pokemon", produces = "application/json")
    public ObjectResponse<PokemonStatistics> getPokemonStatistics() {
        var pokemonStatistics = statisticsService.getPokemonStatistics();
        return new ObjectResponse<>(pokemonStatistics, 1);
    }

    /*
    @GetMapping(value = "/{id}", produces = "application/json")
    public ObjectResponse<PokemonStatistics> getPokemonStatistics(@PathVariable Long id) {
        var pokemonStatistics = statisticsService.getStatistics();
        return new ObjectResponse<>(pokemonStatistics, 1);
    }
     */
}
