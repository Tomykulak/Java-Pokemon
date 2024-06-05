package cz.mendelu.ea.pokemon.model.trainer;



import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainerResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private int id;

    private String name;

    public TrainerResponse(Trainer trainer) {
        this.id = trainer.getId();
        this.name = trainer.getName();
    }

    public TrainerResponse toTrainerResponse(Trainer trainer) {
        return new TrainerResponse(trainer);
    }
}
