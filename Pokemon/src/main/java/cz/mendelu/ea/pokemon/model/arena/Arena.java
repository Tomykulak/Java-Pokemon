package cz.mendelu.ea.pokemon.model.arena;


import cz.mendelu.ea.pokemon.model.trainer.Trainer;
import jakarta.persistence.*;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Validated
@Getter
@Setter
public class Arena {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private int id;

    private String name;

    @OneToMany(mappedBy = "arena")
    @EqualsAndHashCode.Exclude
    private Set<Trainer> trainers = new HashSet<>();

    @ManyToOne
    private Trainer trainer;
}