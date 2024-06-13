package cz.mendelu.ea.pokemon.utils.data;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import cz.mendelu.ea.pokemon.domain.arena.Arena;
import cz.mendelu.ea.pokemon.domain.arena.ArenaService;
import cz.mendelu.ea.pokemon.domain.pokemon.Pokemon;
import cz.mendelu.ea.pokemon.domain.pokemon.PokemonService;
import cz.mendelu.ea.pokemon.domain.trainer.Trainer;
import cz.mendelu.ea.pokemon.domain.trainer.TrainerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DataImporterUnitTest {

    @Mock
    private ResourceLoader resourceLoader;

    @Mock
    private PokemonService pokemonService;

    @Mock
    private TrainerService trainerService;

    @Mock
    private ArenaService arenaService;

    @InjectMocks
    private DataImporter dataImporter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testImportArenas() throws IOException {
        Resource mockResource = mock(Resource.class);
        when(resourceLoader.getResource("classpath:data/arena.csv")).thenReturn(mockResource);
        when(mockResource.getFile()).thenReturn(new File("src/test/resources/data/arena.csv"));

        dataImporter.importArenas();

        verify(arenaService, times(1)).saveMany(anyList());
    }

    @Test
    public void testParseArenas() throws IOException, CsvValidationException {
        Resource mockResource = mock(Resource.class);
        when(mockResource.getFile()).thenReturn(new File("src/test/resources/data/arena.csv"));

        List<Arena> arenas = dataImporter.parseArenas(mockResource);

        assertEquals(3, arenas.size());
        assertEquals("Dragon's Den", arenas.get(0).getName());
    }

    @Test
    public void testImportPokemons() throws IOException {
        Resource mockResource = mock(Resource.class);
        when(resourceLoader.getResource("classpath:data/pokemon.csv")).thenReturn(mockResource);
        when(mockResource.getFile()).thenReturn(new File("src/test/resources/data/pokemon.csv"));

        dataImporter.importPokemons();

        verify(pokemonService, times(1)).saveMany(anyList());
    }

    @Test
    public void testParsePokemons() throws IOException, CsvValidationException {
        Trainer mockTrainer = new Trainer();
        when(trainerService.findById(anyLong())).thenReturn(mockTrainer);

        Resource mockResource = mock(Resource.class);
        when(mockResource.getFile()).thenReturn(new File("src/test/resources/data/pokemon.csv"));

        List<Pokemon> pokemons = dataImporter.parsePokemons(mockResource);

        assertEquals(3, pokemons.size());
        assertEquals("Bulbasaur", pokemons.get(0).getName());
    }

    @Test
    public void testImportTrainers() throws IOException {
        Resource mockResource = mock(Resource.class);
        when(resourceLoader.getResource("classpath:data/trainer.csv")).thenReturn(mockResource);
        when(mockResource.getFile()).thenReturn(new File("src/test/resources/data/trainer.csv"));

        dataImporter.importTrainers();

        verify(trainerService, times(1)).saveMany(anyList());
    }

    @Test
    public void testParseTrainers() throws IOException, CsvValidationException {
        Arena mockArena = new Arena();
        when(arenaService.getArenaById(anyLong())).thenReturn(mockArena);

        Resource mockResource = mock(Resource.class);
        when(mockResource.getFile()).thenReturn(new File("src/test/resources/data/trainer.csv"));

        List<Trainer> trainers = dataImporter.parseTrainers(mockResource);

        assertEquals(3, trainers.size());
        assertEquals("Ash", trainers.get(0).getName());
    }
}
