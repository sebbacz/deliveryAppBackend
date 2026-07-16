package be.kdg.dishsg.security.adapters.in.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// Request body for signing in with an email and password.
public class SignInRequest {

    @Email
    @NotBlank
    public String email;

    @NotBlank
    public String password;
}
