package be.sebastiangondek.kdg.restaurants.domain;

import java.util.UUID;

//  domain record identifying an authenticated restaurant owner.
public record Owner(UUID id, String email, String firstName, String lastName) {
}
