package cz.mendelu.ea.pokemon.model.pokemon;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//
@Entity
//
@Data
@Getter
@Setter
public class Pokemon {

    @Id
    private int id;

    private String name;

    // battle stats
    private Element element;
    private int hp;
    private int attack;
    private int defense;

    enum Element {
        GRASS,
        FIRE,
        WATER,
        ELECTRIC
    }
}