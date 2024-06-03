package cz.mendelu.ea.pokemon.model.pokemon;

import lombok.Data;

@Data
public class PokemonResponse {
    private int id;
    private String name;

    public PokemonResponse(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
