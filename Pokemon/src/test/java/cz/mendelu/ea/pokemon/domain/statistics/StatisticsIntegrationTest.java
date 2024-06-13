package cz.mendelu.ea.pokemon.domain.statistics;

import cz.mendelu.ea.pokemon.domain.arena.Arena;
import cz.mendelu.ea.pokemon.domain.arena.ArenaService;
import cz.mendelu.ea.pokemon.domain.pokemon.Pokemon;
import cz.mendelu.ea.pokemon.domain.pokemon.PokemonRepository;
import cz.mendelu.ea.pokemon.domain.pokemon.PokemonResponse;
import cz.mendelu.ea.pokemon.domain.pokemon.PokemonService;
import cz.mendelu.ea.pokemon.domain.trainer.Trainer;
import cz.mendelu.ea.pokemon.domain.trainer.TrainerService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StatisticsIntegrationTest {

    @LocalServerPort
    private int port;

    @Mock
    private PokemonRepository pokemonRepository;

    @Mock
    private PokemonService pokemonService;

    @Mock
    private TrainerService trainerService;

    @Mock
    private ArenaService arenaService;

    @InjectMocks
    private StatisticsService statisticsService;

    private Pokemon pokemon1;
    private Pokemon pokemon2;
    private Trainer trainer1;
    private Trainer trainer2;
    private Arena arena1;
    private Arena arena2;

    @BeforeEach
    public void setUp() {
        pokemon1 = new Pokemon();
        pokemon1.setAttack(40);
        pokemon1.setDefense(60);
        pokemon1.setSpeed(70);

        pokemon2 = new Pokemon();
        pokemon2.setAttack(60);
        pokemon2.setDefense(40);
        pokemon2.setSpeed(90);

        trainer1 = new Trainer();
        trainer1.setWins(10);
        trainer1.setLosses(5);

        trainer2 = new Trainer();
        trainer2.setWins(8);
        trainer2.setLosses(7);

        arena1 = new Arena();
        arena2 = new Arena();
    }

    @Test
    public void testCountPokemons() {
        when(pokemonService.findAll()).thenReturn(Arrays.asList(pokemon1, pokemon2));

        Map<String, Long> result = statisticsService.countPokemons();
        assertEquals(2, result.get("pokemonCount"));
    }

    @Test
    public void testCountTrainers() {
        when(trainerService.findAll()).thenReturn(Arrays.asList(trainer1, trainer2));

        Map<String, Long> result = statisticsService.countTrainers();
        assertEquals(2, result.get("trainerCount"));
    }

    @Test
    public void testCountArenas() {
        when(arenaService.findAll()).thenReturn(Arrays.asList(arena1, arena2));

        Map<String, Long> result = statisticsService.countArenas();
        assertEquals(2, result.get("arenaCount"));
    }

    @Test
    public void testFindHighestAttackPokemon() {
        when(pokemonService.findAll()).thenReturn(Arrays.asList(pokemon1, pokemon2));

        PokemonResponse result = statisticsService.findHighestAttackPokemon();
        assertEquals(80, result.getAttack());
    }

    @Test
    public void testFindHighestDefensePokemon() {
        when(pokemonService.findAll()).thenReturn(Arrays.asList(pokemon1, pokemon2));

        PokemonResponse result = statisticsService.findHighestDefensePokemon();
        assertEquals(60, result.getDefense());
    }

    @Test
    public void testFindFastestPokemon() {
        when(pokemonService.findAll()).thenReturn(Arrays.asList(pokemon1, pokemon2));

        PokemonResponse result = statisticsService.findFastestPokemon();
        assertEquals(90, result.getSpeed());
    }

    @Test
    public void testFindHighestAttackPokemon_NotFound() {
        when(pokemonService.findAll()).thenReturn(Collections.emptyList());

        assertThrows(ResponseStatusException.class, () -> statisticsService.findHighestAttackPokemon());
    }

    @Test
    public void testCalculateAverageWinRate() {
        when(trainerService.findAll()).thenReturn(Arrays.asList(trainer1, trainer2));

        String result = statisticsService.calculateAverageWinRate();
        assertEquals("60,00%", result);
    }

    @Test
    public void testCalculateAveragePokemonsAttack() {
        List<Pokemon> pokemons = Arrays.asList(pokemon1, pokemon2);

        double result = statisticsService.calculateAveragePokemonsAttack(pokemons);
        assertEquals(65.0, result);
    }

    @Test
    public void testCalculateAveragePokemonsDefense() {
        List<Pokemon> pokemons = Arrays.asList(pokemon1, pokemon2);

        double result = statisticsService.calculateAveragePokemonsDefense(pokemons);
        assertEquals(50.0, result);
    }

    @Test
    public void testGetStatistics() {
        when(pokemonService.findAll()).thenReturn(Arrays.asList(pokemon1, pokemon2));
        when(trainerService.findAll()).thenReturn(Arrays.asList(trainer1, trainer2));
        when(arenaService.findAll()).thenReturn(Arrays.asList(arena1, arena2));

        Statistics result = statisticsService.getStatistics();


        assertEquals(2, result.getCountPokemons().get("pokemonCount"));
        assertEquals(2, result.getCountTrainers().get("trainerCount"));
        assertEquals(2, result.getCountArenas().get("arenaCount"));
    }

    @Test
    public void testGetTrainerStatistics() {
        when(trainerService.findAll()).thenReturn(Arrays.asList(trainer1, trainer2));

        TrainerStatistics result = statisticsService.getTrainerStatistics();

        assertEquals("60,00%", result.getCalculateAverageWinRate());
    }

    @Test
    public void testGetPokemonStatistics() {
        when(pokemonService.findAll()).thenReturn(Arrays.asList(pokemon1, pokemon2));

        PokemonStatistics result = statisticsService.getPokemonStatistics();

        assertEquals(50.0, result.getCalculateAveragePokemonsAttack());
        assertEquals(50.0, result.getCalculateAveragePokemonsDefense());
        assertEquals(60, result.getFindHighestAttackPokemon().getAttack());
        assertEquals(60, result.getFindHighestDefensePokemon().getDefense());
        assertEquals(90, result.getFindFastestPokemon().getSpeed());
    }

    @Test
    public void testGetPokemonStatistics_NoPokemons() {
        when(pokemonService.findAll()).thenReturn(Collections.emptyList());

        assertThrows(ResponseStatusException.class, () -> {
            statisticsService.findHighestAttackPokemon();
        });

        assertThrows(ResponseStatusException.class, () -> {
            statisticsService.findHighestDefensePokemon();
        });

        assertThrows(ResponseStatusException.class, () -> {
            statisticsService.findFastestPokemon();
        });
    }
}

