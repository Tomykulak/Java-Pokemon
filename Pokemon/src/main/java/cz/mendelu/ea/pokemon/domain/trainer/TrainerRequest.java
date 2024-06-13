package cz.mendelu.ea.pokemon.domain.trainer;

import cz.mendelu.ea.pokemon.domain.arena.ArenaService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TrainerRequest {
    private long id;
    @NotNull
    private String trainerName;
    @NotNull
    private Integer level;
    @NotNull
    private Integer wins;
    @NotNull
    private Integer losses;
    @NotNull
    private Long arenaId;

    public TrainerRequest(String updatedTrainer, int level, int wins, int losses, long arenaId) {
        this.trainerName = updatedTrainer;
        this.level = level;
        this.wins = wins;
        this.losses = losses;
        this.arenaId = arenaId;
    }

    public Trainer toTrainer(Trainer trainer, ArenaService arenaService) {
        trainer.setName(this.trainerName);
        trainer.setLevel(this.level);
        trainer.setWins(this.wins);
        trainer.setLosses(this.losses);
        trainer.setArena(arenaService.getArenaById(this.arenaId));
        return trainer;
    }

}
