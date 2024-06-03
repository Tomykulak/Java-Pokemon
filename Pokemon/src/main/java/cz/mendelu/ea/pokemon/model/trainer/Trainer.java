package cz.mendelu.ea.pokemon.model.trainer;

import com.sun.istack.NotNull;
import cz.mendelu.ea.pokemon.model.pokemon.Pokemon;
import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

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
    @GeneratedValue(strategy = GenerationType.UUID)
    @NotNull
    private int id;

    private String name;

    @OneToMany(mappedBy = "trainer")
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Set<Pokemon> pokemons = new HashSet<Pokemon>();

}