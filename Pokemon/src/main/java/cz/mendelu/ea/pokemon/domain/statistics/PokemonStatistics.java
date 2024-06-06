package cz.mendelu.ea.pokemon.domain.statistics;

import cz.mendelu.ea.pokemon.domain.pokemon.PokemonResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PokemonStatistics {
    double calculateAveragePokemonDefense;
    double calculateAveragePokemonAttack;
    PokemonResponse findHighestAttackPokemon;
    PokemonResponse findHighestDefensePokemon;
    PokemonResponse findFastestPokemon;
}
