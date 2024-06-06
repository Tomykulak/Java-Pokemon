package cz.mendelu.ea.pokemon.utils;

import cz.mendelu.ea.pokemon.domain.arena.Arena;
import cz.mendelu.ea.pokemon.domain.arena.ArenaService;
import cz.mendelu.ea.pokemon.utils.data.DataImporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.core.io.Resource;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class DataImporterUnitTest {
    @Mock
    private ArenaService arenaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testImport() throws Exception {
        // Given
        Resource mockResource = mock(Resource.class);
        when(mockResource.getFile()).thenReturn(new File("src/test/resources/data/arena.csv"));
        DataImporter dataImporter = new DataImporter(null, null, null, arenaService);

        // Mocking the arenaService to return a list of three arenas
        List<Arena> arenas = Arrays.asList(new Arena(), new Arena(), new Arena());
        when(arenaService.findAll()).thenReturn(arenas);

        // When
        dataImporter.parseArenas(mockResource);
        var arenaCount = arenaService.findAll().size();

        // Then
        assertThat(arenaCount, equalTo(3));
    }
}
