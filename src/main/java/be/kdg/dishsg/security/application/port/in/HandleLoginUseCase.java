package be.kdg.dishsg.security.application.port.in;

import be.kdg.dishsg.security.domain.Owner;

public  interface HandleLoginUseCase {

    Owner handleLogin(String subjectId, String email, String firstName, String lastName);
}
