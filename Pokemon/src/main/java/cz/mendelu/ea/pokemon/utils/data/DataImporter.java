package cz.mendelu.ea.pokemon.utils.data;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import cz.mendelu.ea.pokemon.domain.arena.Arena;
import cz.mendelu.ea.pokemon.domain.arena.ArenaService;
import cz.mendelu.ea.pokemon.domain.pokemon.Pokemon;
import cz.mendelu.ea.pokemon.domain.pokemon.PokemonService;
import cz.mendelu.ea.pokemon.domain.trainer.Trainer;
import cz.mendelu.ea.pokemon.domain.trainer.TrainerService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DataImporter {
    private final ResourceLoader resourceLoader;

    private final PokemonService pokemonService;
    private final TrainerService trainerService;
    private final ArenaService arenaService;

    @Autowired
    public DataImporter(ResourceLoader resourceLoader, PokemonService pokemonService, TrainerService trainerService, ArenaService arenaService) {
        this.resourceLoader = resourceLoader;
        this.pokemonService = pokemonService;
        this.trainerService = trainerService;
        this.arenaService = arenaService;
    }

    @PostConstruct
    @Transactional
    public void importArenas() {
        log.info("Importing arenas...");
        Resource resource = resourceLoader.getResource("classpath:data/arena.csv");
        List<Arena> arenaList = parseArenas(resource);
        arenaService.saveMany(arenaList);  // Assuming there's a batch save method in pokemonService
    }

    // load arenas
    public List<Arena> parseArenas(Resource resource) {
        List<Arena> arenas = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(resource.getFile()))) {
            reader.readNext();
            String[] line;
            // id,name,type
            while ((line = reader.readNext()) != null) {
                Arena arena = new Arena();
                arena.setId(Long.valueOf(line[0]));
                arena.setName(line[1]);
                arena.setType(line[2]);
                arenas.add(arena);
            }
            log.info("Parsed {} arenas", arenas.size());
        } catch (IOException | CsvValidationException e) {
            log.error("Error reading or parsing the CSV file: ", e);
        }
        return arenas;
    }


    @PostConstruct
    @Transactional
    public void importPokemons() {
        log.info("Importing pokemons...");
        Resource resource = resourceLoader.getResource("classpath:data/pokemon.csv");
        List<Pokemon> pokemonList = parsePokemons(resource);
        pokemonService.saveMany(pokemonList);  // Assuming there's a batch save method in pokemonService
    }
    // load pokemon
    public List<Pokemon> parsePokemons(Resource resource) {
        List<Pokemon> pokemons = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(resource.getFile()))) {
            reader.readNext();
            String[] line;
            // number,name,type1,type2,total,hp,attack,defense,sp_attack,sp_defense,speed,generation,legendary
            while ((line = reader.readNext()) != null) {
                Pokemon pokemon = new Pokemon();
                pokemon.setName(line[1]);
                pokemon.setType1(line[2]);
                pokemon.setType2(line[3]);
                pokemon.setTotal(Integer.parseInt(line[4]));
                pokemon.setHp(Integer.parseInt(line[5]));
                pokemon.setAttack(Integer.parseInt(line[6]));
                pokemon.setDefense(Integer.parseInt(line[7]));
                pokemon.setSp_attack(Integer.parseInt(line[8]));
                pokemon.setSp_defense(Integer.parseInt(line[9]));
                pokemon.setSpeed(Integer.parseInt(line[10]));
                pokemon.setGeneration(Integer.parseInt(line[11]));
                pokemon.setLegendary(Boolean.parseBoolean(line[12]));

                int trainerId = Integer.parseInt(line[13]);
                Trainer trainer = trainerService.findById(Long.valueOf(trainerId));
                pokemon.setTrainer(trainer);
                pokemons.add(pokemon);
            }
            log.info("Parsed {} pokemons", pokemons.size());
        } catch (IOException | CsvValidationException e) {
            log.error("Error reading or parsing the CSV file: ", e);
        }
        return pokemons;
    }


    @PostConstruct
    @Transactional
    public void importTrainers() {
        log.info("Importing trainers...");
        Resource resource = resourceLoader.getResource("classpath:data/trainer.csv");
        List<Trainer> trainerList = parseTrainers(resource);
        trainerService.saveMany(trainerList);
    }

    // load trainers
    public List<Trainer> parseTrainers(Resource resource) {
        List<Trainer> trainers = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(resource.getFile()))) {
            reader.readNext();
            String[] line;
            // id,name,level,wins,losses,pokemon_id
            while ((line = reader.readNext()) != null) {
                Trainer trainer = new Trainer();
                trainer.setId(Long.valueOf((line[0])));
                trainer.setName(line[1]);
                trainer.setLevel(Integer.parseInt(line[2]));
                trainer.setWins(Integer.parseInt(line[3]));
                trainer.setLosses(Integer.parseInt(line[4]));
                Arena arena = arenaService.getArenaById(Long.valueOf(line[5]));
                trainer.setArena(arena);
                trainers.add(trainer);
            }
            log.info("Parsed {} trainers", trainers.size());
        } catch (IOException | CsvValidationException e) {
            log.error("Error reading or parsing the CSV file: ", e);
        }
        return trainers;
    }
}