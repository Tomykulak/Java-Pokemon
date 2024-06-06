package cz.mendelu.ea.pokemon.domain.statistics;

import cz.mendelu.ea.pokemon.domain.pokemon.PokemonResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class Statistics {
    Map<String, Long> countPokemons;
    Map<String, Long> countTrainers;
    Map<String, Long> countArenas;
    // PokemonResponse findHighestAttackPokemon;
    // PokemonResponse findHighestDefensePokemon;
}
