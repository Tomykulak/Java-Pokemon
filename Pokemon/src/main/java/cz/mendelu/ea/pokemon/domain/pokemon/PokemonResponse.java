package cz.mendelu.ea.pokemon.domain.pokemon;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PokemonResponse {
    private Long id;
    @NotEmpty
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

    public PokemonResponse(Pokemon pokemon) {
        this.id = pokemon.getId();
        this.name = pokemon.getName();
        this.type1 = pokemon.getType1();
        this.type2 = pokemon.getType2();
        this.total = pokemon.getTotal();
        this.hp = pokemon.getHp();
        this.attack = pokemon.getAttack();
        this.defense = pokemon.getDefense();
        this.sp_attack = pokemon.getSp_attack();
        this.sp_defense = pokemon.getSp_defense();
        this.speed = pokemon.getSpeed();
        this.generation = pokemon.getGeneration();
        this.legendary = pokemon.isLegendary();
    }
}
