package cz.mendelu.ea.pokemon.model.pokemon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PokemonService {
    private PokemonRepository pokemonRepository;

    @Autowired
    PokemonService(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }
}
