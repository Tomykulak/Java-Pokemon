package cz.mendelu.ea.pokemon.model.pokemon;

import lombok.Data;


@Data
public class PokemonResponse {
    private Long id;
    private String name;

    public PokemonResponse(Pokemon pokemon) {
        this.id = pokemon.getId();
        this.name = pokemon.getName();
    }
}
