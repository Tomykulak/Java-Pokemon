package cz.mendelu.ea.pokemon.model.trainer;

import cz.mendelu.ea.pokemon.model.arena.Arena;
import cz.mendelu.ea.pokemon.model.pokemon.Pokemon;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int level;

    private int wins;
    private int losses;

    //db relations
    @OneToMany(mappedBy = "trainer")
    @EqualsAndHashCode.Exclude
    private Set<Pokemon> pokemons = new HashSet<Pokemon>();

    @ManyToOne
    private Arena arena;
}