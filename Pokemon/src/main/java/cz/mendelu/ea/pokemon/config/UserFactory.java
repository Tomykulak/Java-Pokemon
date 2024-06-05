package cz.mendelu.ea.pokemon.config;

import com.nimbusds.jose.shaded.gson.internal.LinkedTreeMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFactory {

    @Value("${keycloak.client-id}")
    private String clientId;

    public User of(Jwt jwt) {
        List<String> roles = List.of();

        LinkedTreeMap<String, LinkedTreeMap<String, List<String>>> resourceAccess = jwt.getClaim("resource_access");
        var clientRoles = resourceAccess.get(clientId);

        if (clientRoles != null && clientRoles.get("roles") != null) {
            roles = clientRoles.get("roles");
        }

        return new User(
                jwt.getClaim("preferred_username"),
                jwt.getClaim("name"),
                jwt.getClaim("email"),
                roles
        );
    }

}
