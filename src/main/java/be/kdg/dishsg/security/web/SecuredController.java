package be.kdg.dishsg.security.web;

import be.kdg.dishsg.security.web.dto.SecuredMessageDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secured")
public class SecuredController {

    @GetMapping("message")
    @PreAuthorize("hasAuthority('owner')")
    public SecuredMessageDto getMessage(@AuthenticationPrincipal Jwt token) {
        String email = token.getClaimAsString(StandardClaimNames.EMAIL);
        String firstName = token.getClaimAsString(StandardClaimNames.GIVEN_NAME);
        String lastName = token.getClaimAsString(StandardClaimNames.FAMILY_NAME);

        SecuredMessageDto dto = new SecuredMessageDto();
        dto.setEmail(email);
        dto.setFirstName(firstName);
        dto.setLastName(lastName);
        dto.setMessage("Welcome to your restaurant management area!");
        return dto;
    }
}
