package cz.mendelu.ea.pokemon.model.arena;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ArenaRepository extends CrudRepository<Arena, Long> {
}