package cz.mendelu.ea.pokemon.domain.arena;

import cz.mendelu.ea.pokemon.domain.trainer.Trainer;
import cz.mendelu.ea.pokemon.utils.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArenaService {

    private final ArenaRepository arenaRepository;

    @Autowired
    public ArenaService(ArenaRepository arenaRepository) {
        this.arenaRepository = arenaRepository;
    }

    // save into database
    public void saveMany(List<Arena> arenas) {
        arenaRepository.saveAll(arenas);
    }

    public void addTrainerToArena(Long arenaId, Trainer trainer) {
        Arena arena = arenaRepository.findById(arenaId).orElseThrow(() -> new NotFoundException());
        arena.getTrainers().add(trainer);
        arenaRepository.save(arena);
    }

    public void deleteArena(Long arenaId) {
        arenaRepository.deleteById(arenaId);
    }

    public Arena getArenaById(Long arenaId) {
        return arenaRepository.findById(arenaId).orElseThrow(() -> new NotFoundException());
    }

    public List<Arena> findAll() {
        return (List<Arena>) arenaRepository.findAll();
    }
}