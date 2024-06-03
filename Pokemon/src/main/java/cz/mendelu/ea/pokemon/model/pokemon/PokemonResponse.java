package cz.mendelu.ea.pokemon.model.pokemon;

import lombok.Data;

import java.util.UUID;

@Data
public class PokemonResponse {
    private UUID id;
    private String name;

    public PokemonResponse(Pokemon pokemon) {
        this.id = pokemon.getId();
        this.name = pokemon.getName();
    }
}
