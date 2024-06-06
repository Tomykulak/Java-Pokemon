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

    public PokemonResponse createPokemon(PokemonRequest pokemonRequest) {
        Pokemon pokemon = getPokemon(pokemonRequest);

        pokemon = pokemonRepository.save(pokemon);

        return new PokemonResponse(
                pokemon.getId(),
                pokemon.getName(),
                pokemon.getType1(),
                pokemon.getType2(),
                pokemon.getTotal(),
                pokemon.getHp(),
                pokemon.getAttack(),
                pokemon.getDefense(),
                pokemon.getSp_attack(),
                pokemon.getSp_defense(),
                pokemon.getSpeed(),
                pokemon.getGeneration(),
                pokemon.isLegendary()
        );
    }

    private static Pokemon getPokemon(PokemonRequest pokemonRequest) {
        Pokemon pokemon = new Pokemon();
        pokemon.setName(pokemonRequest.getName());
        pokemon.setType1(pokemonRequest.getType1());
        pokemon.setType2(pokemonRequest.getType2());
        pokemon.setTotal(pokemonRequest.getTotal());
        pokemon.setHp(pokemonRequest.getHp());
        pokemon.setAttack(pokemonRequest.getAttack());
        pokemon.setDefense(pokemonRequest.getDefense());
        pokemon.setSp_attack(pokemonRequest.getSp_attack());
        pokemon.setSp_defense(pokemonRequest.getSp_defense());
        pokemon.setSpeed(pokemonRequest.getSpeed());
        pokemon.setGeneration(pokemonRequest.getGeneration());
        pokemon.setLegendary(pokemonRequest.isLegendary());
        return pokemon;
    }
}
