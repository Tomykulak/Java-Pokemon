package cz.mendelu.ea.pokemon.domain.arena;

import cz.mendelu.ea.pokemon.domain.trainer.Trainer;
import org.springframework.stereotype.Service;
import cz.mendelu.ea.pokemon.utils.exceptions.NotFoundException;

import java.util.List;

@Service
public class ArenaService {
    ArenaRepository arenaRepository;

    public ArenaService(ArenaRepository arenaRepository) {
        this.arenaRepository = arenaRepository;
    }

    // save into database
    public void saveMany(List<Arena> arenas) {
        arenaRepository.saveAll(arenas);
    }

    public void addTrainerToArena(Long arenaId, Trainer trainer) {
        Arena arena = arenaRepository.findById(arenaId).orElseThrow(NotFoundException::new);
        arena.getTrainers().add(trainer);
        arenaRepository.save(arena);
    }

    public void deleteArena(Long arenaId) {
        arenaRepository.deleteById(arenaId);
    }

    public Arena getArenaById(Long arenaId) {
        return arenaRepository.findById(arenaId).orElseThrow(NotFoundException::new);
    }

    public List<Arena> getAllArenas() {
        return (List<Arena>) arenaRepository.findAll();
    }
}
