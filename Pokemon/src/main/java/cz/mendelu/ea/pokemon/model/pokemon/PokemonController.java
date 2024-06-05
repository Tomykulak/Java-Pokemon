package cz.mendelu.ea.pokemon.model.pokemon;

import cz.mendelu.ea.pokemon.utils.response.ArrayResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
@RequestMapping("/pokemons")
@Validated
public class PokemonController {

    @Autowired
    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }
    private final PokemonService pokemonService;

    @GetMapping(value = "", produces = "application/json")
    @Valid
    public ArrayResponse<PokemonResponse> getAllPokemons() {
        List<Pokemon> pokemons = pokemonService.findAll();
        return ArrayResponse.of(
                pokemons,
                PokemonResponse::new
        );
    }
}
