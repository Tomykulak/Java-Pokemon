package cz.mendelu.ea.pokemon.domain.trainer;



import cz.mendelu.ea.pokemon.domain.arena.Arena;
import cz.mendelu.ea.pokemon.domain.pokemon.Pokemon;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainerResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;

    private String name;

    private int level;

    private int wins;
    private int losses;


    public TrainerResponse(Trainer trainer) {
        this.id = trainer.getId();
        this.name = trainer.getName();
        this.level = trainer.getLevel();
        this.wins = trainer.getWins();
        this.losses = trainer.getLosses();
    }

    public TrainerResponse toTrainerResponse(Trainer trainer) {
        return new TrainerResponse(trainer);
    }
}