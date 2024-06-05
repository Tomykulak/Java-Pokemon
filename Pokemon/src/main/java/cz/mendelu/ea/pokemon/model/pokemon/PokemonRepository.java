package cz.mendelu.ea.pokemon.model.pokemon;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PokemonRepository extends CrudRepository<Pokemon, Long> {

}
