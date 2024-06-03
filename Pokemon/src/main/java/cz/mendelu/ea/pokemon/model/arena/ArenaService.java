package cz.mendelu.ea.pokemon.model.arena;

import cz.mendelu.ea.pokemon.model.trainer.Trainer;
import org.springframework.stereotype.Service;
import cz.mendelu.ea.pokemon.utils.exceptions.NotFoundException;

import java.util.List;
import java.util.UUID;

@Service
public class ArenaService {
    ArenaRepository arenaRepository;

    public ArenaService(ArenaRepository arenaRepository) {
        this.arenaRepository = arenaRepository;
    }

    public void addTrainerToArena(UUID arenaId, Trainer trainer) {
        Arena arena = arenaRepository.findById(arenaId).orElseThrow(NotFoundException::new);
        arena.getTrainers().add(trainer);
        arenaRepository.save(arena);
    }

    public void deleteArena(UUID arenaId) {
        arenaRepository.deleteById(arenaId);
    }

    public Arena getArenaById(UUID arenaId) {
        return arenaRepository.findById(arenaId).orElseThrow(NotFoundException::new);
    }
    public List<Arena> getAllArenas() {
        return (List<Arena>) arenaRepository.findAll();
    }
}
