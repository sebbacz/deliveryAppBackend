package be.kdg.dishsg.security.adapters.in.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// Request body for registering a new owner account (email, password >= 8 chars, first and last name).
public class SignUpRequest {

    @Email
    @NotBlank
    public String email;

    @NotBlank
    @Size(min = 8)
    public String password;

    @NotBlank
    public String firstName;

    @NotBlank
    public String lastName;
}
