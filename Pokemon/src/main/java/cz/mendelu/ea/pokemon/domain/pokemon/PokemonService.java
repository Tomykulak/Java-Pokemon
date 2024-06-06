package cz.mendelu.ea.pokemon.domain.pokemon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PokemonService {
    private final PokemonRepository pokemonRepository;

    @Autowired
    PokemonService(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    public void saveMany(List<Pokemon> pokemons){
        pokemonRepository.saveAll(pokemons);
    }

    public Optional<Pokemon> findById(Long id) {
        return pokemonRepository.findById(id);
    }

    public List<Pokemon> findAll() {
        List<Pokemon> pokemons = new ArrayList<>();
        pokemonRepository.findAll().forEach(pokemons::add);
        return pokemons;
    }
    public Pokemon createPokemon(Pokemon pokemon) {
        return pokemonRepository.save(pokemon);
    }

    public Pokemon updatePokemon(Long id, Pokemon pokemon) {
        pokemon.setId(id);
        return pokemonRepository.save(pokemon);
    }
}
