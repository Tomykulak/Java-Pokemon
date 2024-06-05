package cz.mendelu.ea.pokemon.model.trainer;

import cz.mendelu.ea.pokemon.model.arena.ArenaService;
import cz.mendelu.ea.pokemon.model.pokemon.Pokemon;
import cz.mendelu.ea.pokemon.model.pokemon.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TrainerService {
    private final TrainerRepository trainerRepository;

    private final PokemonService pokemonService;
    private final ArenaService arenaService;

    @Autowired
    public TrainerService(TrainerRepository trainerRepository, PokemonService pokemonService, ArenaService arenaService) {
        this.trainerRepository = trainerRepository;
        this.pokemonService = pokemonService;
        this.arenaService = arenaService;
    }

    public void saveMany(List<Trainer> trainers) {
        trainerRepository.saveAll(trainers);
    }

    public TrainerResponse createTrainer(TrainerRequest trainerRequest) {
        Trainer trainer = new Trainer();
        trainer.setName(
                trainerRequest.getTrainerName()
        );
        trainerRepository.save(trainer);
        return new TrainerResponse(trainer);
    }

    public Trainer findById(Long id) {
        return trainerRepository.findById(id).orElse(null);
    }

    public List<Trainer> findAll() {
        List<Trainer> trainers = new ArrayList<>();
        trainerRepository.findAll().forEach(trainers::add);
        return trainers;
    }
}
