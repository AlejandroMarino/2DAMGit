package jakarta.security;

import common.Constants;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.credential.BasicAuthenticationCredential;
import jakarta.security.enterprise.credential.RememberMeCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.HttpHeaders;

import java.io.IOException;

@ApplicationScoped
public class JWTAuth implements HttpAuthenticationMechanism {
    @Inject
    private MyIdentityStore identityStore;

    private final GenerateToken gT;

    @Inject
    public JWTAuth(GenerateToken gT) {
        this.gT = gT;
    }

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) throws AuthenticationException {
        CredentialValidationResult result = CredentialValidationResult.INVALID_RESULT;
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null) {
            String[] parts = header.split(Constants.SPACE);

            if (parts[0].equalsIgnoreCase(Constants.BASIC)) {

                result = identityStore.validate(new BasicAuthenticationCredential(parts[1]));

                if (result.getStatus() == CredentialValidationResult.Status.VALID) {
                    String token = gT.generateToken(result.getCallerPrincipal().getName(), result.getCallerGroups().stream().toList());
                    response.setHeader(HttpHeaders.AUTHORIZATION, token);
                }
            } else if (parts[0].equalsIgnoreCase(Constants.BEARER)) {
                try {
                    result = identityStore.validate(new RememberMeCredential(parts[1]));
                } catch (ExpiredJwtException e) {
                    try {
                        response.sendError(Constants.INVALID_TOKEN);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } catch (Exception e) {
                    try {
                        response.sendError(Constants.UNAUTHORIZED);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }

        }
        if (!result.getStatus().equals(CredentialValidationResult.Status.VALID)) {
            request.setAttribute(Constants.STATUS, result.getStatus());
            return httpMessageContext.doNothing();
        }

        return httpMessageContext.notifyContainerAboutLogin(result);
    }
}
