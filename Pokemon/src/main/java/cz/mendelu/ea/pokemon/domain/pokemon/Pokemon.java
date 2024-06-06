package cz.mendelu.ea.pokemon.domain.pokemon;


import cz.mendelu.ea.pokemon.domain.trainer.Trainer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Pokemon {
    // number,name,type1,type2,total,hp,attack,defense,sp_attack,sp_defense,speed,generation,legendary
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    // battle stats
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

    //db relations
    @ManyToOne
    private Trainer trainer;
}