package cz.mendelu.ea.pokemon.model.pokemon;

import com.sun.istack.NotNull;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Pokemon {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @NotNull
    private UUID id;

    private String name;

    // battle stats
    private String element; // type1
    private int hp;
    private int attack;
    private int defense;

    /*
    enum Element {
        GRASS,
        FIRE,
        WATER,
        ELECTRIC,
        NORMAL
    }
     */
}