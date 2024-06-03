package cz.mendelu.ea.pokemon.model.pokemon;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pokemons")
@Validated
public class PokemonController {
}
