package be.sebastiangondek.kdg.restaurants.adapters.out.securityConfig;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Extracts Keycloak realm_access roles from a JWT and maps them to Spring Security
public class KeycloakRealmRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
    @SuppressWarnings("unchecked")
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        final Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");
        if (realmAccess == null) return List.of();
        final List<String> roles = (List<String>) realmAccess.get("roles");
        if (roles == null) return List.of();
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
