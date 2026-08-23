package be.sebastiangondek.kdg.restaurants.adapters.in.webAdapters.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// Request body for registering a new owner account
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
