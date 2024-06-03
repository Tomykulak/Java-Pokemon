package cz.mendelu.ea.pokemon.model.trainer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TrainerRequest {
    private String trainerName;

}
