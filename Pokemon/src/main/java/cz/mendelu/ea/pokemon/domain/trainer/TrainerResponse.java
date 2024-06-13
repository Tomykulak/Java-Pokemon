package cz.mendelu.ea.pokemon.domain.trainer;

import lombok.Data;

@Data
public class TrainerResponse {
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
}
