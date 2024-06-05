package cz.mendelu.ea.pokemon.model.pokemon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PokemonService {
    private PokemonRepository pokemonRepository;

    @Autowired
    PokemonService(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    public void saveMany(List<Pokemon> pokemons){
        pokemonRepository.saveAll(pokemons);
    }

    public Optional<Pokemon> getById(Long id) {
        return pokemonRepository.findById(id);
    }

    public List<Pokemon> getAllPokemons() {
        List<Pokemon> pokemons = new ArrayList<>();
        pokemonRepository.findAll().forEach(pokemons::add);
        return pokemons;
    }
}
