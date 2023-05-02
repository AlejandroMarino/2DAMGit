package jakarta.security;

import domain.modelo.NotFoundException;
import domain.models.User;
import domain.servicios.ServicesLogin;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.BasicAuthenticationCredential;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;

public class MyIdentityStore implements IdentityStore {

    private final ServicesLogin sL;

    @Inject
    public MyIdentityStore(ServicesLogin sL) {
        this.sL = sL;
    }

    @Override
    public CredentialValidationResult validate(Credential credential) {
        BasicAuthenticationCredential user = (BasicAuthenticationCredential) credential;
        try {
            User u = sL.validationUser(user.getCaller(), user.getPasswordAsString().toCharArray());
            return new CredentialValidationResult(u.getUsername(), sL.getRoles(u.getUsername()));
        } catch (NotFoundException e) {
            return CredentialValidationResult.INVALID_RESULT;
        }
    }

}
