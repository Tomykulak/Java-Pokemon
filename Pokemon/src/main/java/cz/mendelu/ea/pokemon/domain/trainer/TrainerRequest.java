package cz.mendelu.ea.pokemon.domain.trainer;

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

    public TrainerRequest(String name){
        this.trainerName = name;
    }
}
