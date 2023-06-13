package jakarta.security;

import dao.UsuarioDao;
import jakarta.common.ConstantsRoles;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.BasicAuthenticationCredential;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import lombok.extern.log4j.Log4j2;
import model.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static jakarta.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;
import static jakarta.security.enterprise.identitystore.CredentialValidationResult.NOT_VALIDATED_RESULT;

@Log4j2
@ApplicationScoped
public class BasicIdentityStore implements IdentityStore {
    private UsuarioDao dao;

    public BasicIdentityStore() {
    }

    @Inject
    public BasicIdentityStore(UsuarioDao dao) {
        this.dao = dao;
    }

    @Override
    public int priority() {
        return 10;
    }

    @Override
    public CredentialValidationResult validate(Credential credential) {
        CredentialValidationResult result = null;
        if (credential instanceof BasicAuthenticationCredential user) {
            String userName = user.getCaller();
            try {
                User foundUser = dao.getUsuario(userName);
                if (!Arrays.equals(user.getPassword().getValue(), foundUser.getPassword().toCharArray())) {
                    result = INVALID_RESULT;
                } else {
                    List<String> roles = foundUser.getRoles();
                    if (roles.contains("curioso")) {
                        result = new CredentialValidationResult(userName, Set.of("raton", "curioso"));
                    } else if (roles.contains("biologo")) {
                        result = new CredentialValidationResult(userName, Set.of("raton", "biologo"));
                    } else if (roles.contains("nivel2")) {
                        result = new CredentialValidationResult(userName, Set.of("informe", "nivel2"));
                    } else if (roles.contains("nivel1")) {
                        result = new CredentialValidationResult(userName, Set.of("informe", "nivel1"));
                    } else if (roles.contains("espia")) {
                        result = new CredentialValidationResult(userName, Set.of("informe", "espia"));
                    }
                }
            } catch (Exception e) {
                result = INVALID_RESULT;
                log.error(e.getMessage(), e);
            }
        }
        return result;
    }
}
