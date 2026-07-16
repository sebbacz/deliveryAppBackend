package be.kdg.dishsg.security.adapters.in.web.dto;

// Response DTO carrying the Bearer token issued on successful custom sign-in.
public class AuthTokenResponse {
    public String tokenType;
    public String accessToken;
}
