package jakarta.security;

import common.Constants;
import domain.models.Usuario;
import domain.services.ServicesUsuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.credential.BasicAuthenticationCredential;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.RememberMeCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;

import java.security.Key;
import java.util.HashSet;
import java.util.List;

import static jakarta.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;

public class MyIdentityStore implements IdentityStore {

    private final Key key;

    private final ServicesUsuario sU;

    @Inject
    public MyIdentityStore(@Named(Constants.JWT) Key key, ServicesUsuario sU) {
        this.key = key;
        this.sU = sU;
    }

    @Override
    public CredentialValidationResult validate(Credential credential) {
        CredentialValidationResult credentialValidationResult = INVALID_RESULT;
        if (credential instanceof RememberMeCredential jwt) {
            String jwtString = jwt.getToken();
            Jws<Claims> jws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwtString);
            String username = (String) jws.getBody().get(Constants.USERNAME);
            List<String> roles = (List<String>) jws.getBody().get(Constants.ROLES);
            if (username != null && roles != null) {
                credentialValidationResult = new CredentialValidationResult(username, new HashSet<>(roles));
            } else {
                credentialValidationResult = INVALID_RESULT;
            }
            return credentialValidationResult;
        }

        if (credential instanceof BasicAuthenticationCredential) {
            BasicAuthenticationCredential basicAuthenticationCredential = (BasicAuthenticationCredential) credential;
            String username = basicAuthenticationCredential.getCaller();
            String password = basicAuthenticationCredential.getPasswordAsString();
            Usuario u = sU.login(new Usuario(username, password));
            if (u != null) {
                List<String> l = u.getRoles();
                if (l != null) {
                    credentialValidationResult = new CredentialValidationResult(u.getNombre(), new HashSet<>(l));
                }else {
                    credentialValidationResult = INVALID_RESULT;
                }
            }
        }
        return credentialValidationResult;
    }

}
