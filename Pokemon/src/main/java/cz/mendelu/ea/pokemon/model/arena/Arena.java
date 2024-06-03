package cz.mendelu.ea.pokemon.model.arena;

import com.sun.istack.NotNull;
import cz.mendelu.ea.pokemon.model.trainer.Trainer;
import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
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
    @NotNull
    private int id;

    private String name;

    @OneToMany(mappedBy = "arena")
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Set<Trainer> trainers = new HashSet<>();

    @ManyToOne
    private Trainer trainer;
}