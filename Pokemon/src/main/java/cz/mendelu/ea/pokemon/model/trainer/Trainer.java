package cz.mendelu.ea.pokemon.model.trainer;

import cz.mendelu.ea.pokemon.model.pokemon.Pokemon;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@Getter
@Setter
public class Trainer {
    @Id
    private int id;

    private String name;

    private Set<Pokemon> pokemons = new HashSet<Pokemon>();

}