package cz.mendelu.ea.pokemon.domain.trainer;

import cz.mendelu.ea.pokemon.domain.arena.ArenaService;
import cz.mendelu.ea.pokemon.domain.pokemon.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public Trainer createTrainer(Trainer trainer) {
        return trainerRepository.save(trainer);
    }

    public Trainer findById(Long id) {
        return trainerRepository.findById(id).orElse(null);
    }

    public List<Trainer> findAll() {
        List<Trainer> trainers = new ArrayList<>();
        trainerRepository.findAll().forEach(trainers::add);
        return trainers;
    }

    public void deleteTrainer(Long id) {
        trainerRepository.deleteById(id);
    }
}