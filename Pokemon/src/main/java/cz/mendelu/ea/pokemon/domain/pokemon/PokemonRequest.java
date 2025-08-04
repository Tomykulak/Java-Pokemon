package cz.mendelu.ea.pokemon.domain.pokemon;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class PokemonRequest {
    private long id;
    @NotNull
    private String name;
    private String type1;
    private String type2;
    private int total;
    private int hp;
    private int attack;
    private int defense;
    private int sp_attack;
    private int sp_defense;
    private int speed;
    private int generation;
    private boolean legendary;

    public PokemonRequest(String name, String type1){
        this.name = name;
        this.type1 = type1;
    }

    // constructor for invalid create test
    public PokemonRequest(String type1, int hp){
        this.type1 = type1;
        this.hp = hp;
    }

    public void toPokemon(Pokemon pokemon){
        pokemon.setId(id);
        pokemon.setName(name);
        pokemon.setType1(type1);
        pokemon.setType2(type2);
        pokemon.setTotal(total);
        pokemon.setHp(hp);
        pokemon.setDefense(defense);
        pokemon.setSp_attack(sp_attack);
        pokemon.setSp_defense(sp_defense);
        pokemon.setSpeed(speed);
        pokemon.setGeneration(generation);
        pokemon.setLegendary(legendary);
    }
}
