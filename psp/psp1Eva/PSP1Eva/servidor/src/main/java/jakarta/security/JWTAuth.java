package jakarta.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
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

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) throws AuthenticationException {
        CredentialValidationResult result = CredentialValidationResult.INVALID_RESULT;
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null) {
            String[] parts = header.split(" ");

            if (parts[0].equalsIgnoreCase("Bearer")) {
                try {
                    result = identityStore.validate(new RememberMeCredential(parts[1]));
                } catch (ExpiredJwtException e) {
                    try {
                        response.sendError(498);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } catch (Exception e) {
                    try {
                        response.sendError(401);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }

        }
        if (!result.getStatus().equals(CredentialValidationResult.Status.VALID)) {
            request.setAttribute("status", result.getStatus());
            return httpMessageContext.doNothing();
        }

        return httpMessageContext.notifyContainerAboutLogin(result);
    }
}
