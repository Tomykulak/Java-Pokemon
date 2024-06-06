package cz.mendelu.ea.pokemon.domain.statistics;

import cz.mendelu.ea.pokemon.domain.arena.ArenaService;
import cz.mendelu.ea.pokemon.domain.pokemon.Pokemon;
import cz.mendelu.ea.pokemon.domain.pokemon.PokemonRepository;
import cz.mendelu.ea.pokemon.domain.pokemon.PokemonResponse;
import cz.mendelu.ea.pokemon.domain.pokemon.PokemonService;
import cz.mendelu.ea.pokemon.domain.trainer.Trainer;
import cz.mendelu.ea.pokemon.domain.trainer.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {
    private final PokemonRepository pokemonRepository;
    private final PokemonService pokemonService;
    private final TrainerService trainerService;
    private final ArenaService arenaService;


    @Autowired
    public StatisticsService(PokemonRepository pokemonRepository, PokemonService pokemonService, TrainerService trainerService, ArenaService arenaService) {
        this.pokemonRepository = pokemonRepository;
        this.pokemonService = pokemonService;
        this.trainerService = trainerService;
        this.arenaService = arenaService;
    }

    public Map<String, Long> countPokemons() {
        long count = pokemonService.findAll().size();
        Map<String, Long> result = new HashMap<>();
        result.put("pokemonCount", count);
        return result;
    }

    public Map<String, Long> countTrainers() {
        long count = trainerService.findAll().size();
        Map<String, Long> result = new HashMap<>();
        result.put("trainerCount", count);
        return result;
    }

    public Map<String, Long> countArenas(){
        long count = arenaService.findAll().size();
        Map<String, Long> result = new HashMap<>();
        result.put("arenaCount", count);
        return result;
    }

    public PokemonResponse findHighestAttackPokemon(){
        return pokemonService.findAll().stream()
                .max(Comparator.comparing(Pokemon::getAttack))
                .map(PokemonResponse::new)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"pokemon not found"));
    }

    public PokemonResponse findHighestDefensePokemon(){
        return pokemonService.findAll().stream()
                .max(Comparator.comparing(Pokemon::getDefense))
                .map(PokemonResponse::new)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"pokemon not found"));
    }
    public PokemonResponse findFastestPokemon(){
        return pokemonService.findAll().stream()
                .max(Comparator.comparing(Pokemon::getSpeed))
                .map(PokemonResponse::new)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"pokemon not found"));
    }


    public Statistics getStatistics() {
        return new Statistics(
                countPokemons(),
                countTrainers(),
                countArenas()
        );
    }

    public TrainerStatistics getTrainerStatistics() {
        return new TrainerStatistics(
                calculateAverageWinRate()
        );
    }

    public String calculateAverageWinRate() {
        double averageWinRate = trainerService.findAll().stream()
                .mapToDouble(trainer -> {
                    int totalBattles = trainer.getWins() + trainer.getLosses();
                    return totalBattles == 0 ? 0.0 : (double) trainer.getWins() / totalBattles * 100;
                })
                .average()
                .orElse(0.0); // Return 0.0 if there are no trainers

        return String.format("%.2f%%", averageWinRate);
    }


    public double calculateAveragePokemonsAttack(List<Pokemon> pokemons){
        return pokemons.stream()
                .mapToInt(Pokemon::getAttack)
                .average()
                .orElse(0.0);
    }

    public double calculateAveragePokemonsDefense(List<Pokemon> pokemons){
        return pokemons.stream()
                .mapToInt(Pokemon::getDefense)
                .average()
                .orElse(0.0);
    }

    public PokemonStatistics getPokemonStatistics() {
        List<Pokemon> pokemons = pokemonService.findAll();
        return new PokemonStatistics(
                calculateAveragePokemonsAttack(pokemons),
                calculateAveragePokemonsDefense(pokemons),
                findHighestAttackPokemon(),
                findHighestDefensePokemon(),
                findFastestPokemon()
        );
    }


}
