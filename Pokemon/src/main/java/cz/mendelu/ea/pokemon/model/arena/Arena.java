package cz.mendelu.ea.pokemon.model.arena;

import cz.mendelu.ea.pokemon.model.trainer.Trainer;
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
public class Arena {
    @Id
    private int id;

    private String name;

    private Set<Trainer> trainers = new HashSet<>();
}