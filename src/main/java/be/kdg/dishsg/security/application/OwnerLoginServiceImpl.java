package be.kdg.dishsg.security.application;


import be.kdg.dishsg.security.application.port.in.HandleLoginUseCase;
import be.kdg.dishsg.security.domain.Owner;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OwnerLoginServiceImpl implements HandleLoginUseCase {

    @Override
    public Owner handleLogin(String subjectId, String email, String firstName, String lastName) {


        //check
        return new Owner(UUID.fromString(subjectId), email, firstName, lastName);
    }
}
