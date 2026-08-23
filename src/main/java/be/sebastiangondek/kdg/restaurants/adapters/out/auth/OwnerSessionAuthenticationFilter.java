package be.sebastiangondek.kdg.restaurants.adapters.out.auth;

import be.sebastiangondek.kdg.restaurants.domain.Owner;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

//  authenticates requests that carry a custom session token
@Component
public class OwnerSessionAuthenticationFilter extends OncePerRequestFilter {

    private final OwnerSessionTokenStore tokenStore;

    public OwnerSessionAuthenticationFilter(OwnerSessionTokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Skip if Keycloak already authenticated this request
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                // Token not found in the store means  its jwt
                tokenStore.findOwnerByToken(token).ifPresent(this::authenticate);
            }
        }
        filterChain.doFilter(request, response);
    }

    private void authenticate(Owner owner) {
        // owner object becomes authpricipial in controllers, same as jwt
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                owner,
                null,
                List.of(new SimpleGrantedAuthority("owner"))
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
