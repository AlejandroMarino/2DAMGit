package jakarta.security;

import domain.modelo.NotFoundException;
import domain.servicios.ServicesLogin;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.di.KeyProvider;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.RememberMeCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;

import java.security.Key;
import java.util.Set;

import static jakarta.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;
import static jakarta.security.enterprise.identitystore.CredentialValidationResult.NOT_VALIDATED_RESULT;

public class MyIdentityStore implements IdentityStore {

    private final Key key;

    @Inject
    public MyIdentityStore(@Named("JWT") Key key) {
        this.key = key;
    }

    @Override
    public CredentialValidationResult validate(Credential credential) {
        CredentialValidationResult credentialValidationResult = INVALID_RESULT;
        if (credential instanceof RememberMeCredential jwt) {
            try {
                String jwtString = jwt.getToken();
                Jws<Claims> jws = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(jwtString);
                String username = (String) jws.getBody().get("username");
                Set<String> roles = (Set<String>) jws.getBody().get("roles");
                if (username != null && roles != null) {
                    credentialValidationResult = new CredentialValidationResult(username, roles);
                } else {
                    credentialValidationResult = INVALID_RESULT;
                }
                return credentialValidationResult;
            } catch (NotFoundException e) {
                credentialValidationResult = NOT_VALIDATED_RESULT;
            } catch (Exception e) {
                credentialValidationResult = INVALID_RESULT;
            }
        }
        return credentialValidationResult;
    }

}
