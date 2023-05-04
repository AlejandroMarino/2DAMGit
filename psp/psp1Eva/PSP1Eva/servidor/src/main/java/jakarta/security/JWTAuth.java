package jakarta.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.security.Key;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
//            if (parts[0].equalsIgnoreCase("Basic")) {
//                result = identityStore.validate(new BasicAuthenticationCredential(parts[1]));
//                if (result.getStatus() == CredentialValidationResult.Status.VALID) {
//                    String jws = Jwts.builder()
//                            .setSubject("servidor")
//                            .setIssuer("me")
//                            .setExpiration(Date.from(LocalDateTime.now().plusMinutes(15).atZone(ZoneId.systemDefault()).toInstant()))
//                            .claim("user", result.getCallerPrincipal().getName())
//                            .claim("roles", result.getCallerGroups())
//                            .signWith(key).compact();
//                    response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jws);
//                }
//          }
            if (parts[0].equalsIgnoreCase("Bearer")) {
                    result = identityStore.validate(new RememberMeCredential(parts[1]));
                    if (result.getStatus().equals(CredentialValidationResult.Status.NOT_VALIDATED)) {
                        httpMessageContext.setResponse((HttpServletResponse) Response.status(498)
                                .entity("Token expires")
                                .type(MediaType.TEXT_PLAIN)
                                .build());
                        httpMessageContext.cleanClientSubject();
                    } else if (result.getStatus().equals(CredentialValidationResult.Status.INVALID)) {
                        httpMessageContext.responseNotFound();
                        httpMessageContext.cleanClientSubject();
                    }
            }
//            else if (parts[0].equalsIgnoreCase("Logout")) {
//                result = CredentialValidationResult.NOT_VALIDATED_RESULT;
//                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
//            }

        }
        if (!result.getStatus().equals(CredentialValidationResult.Status.VALID)) {
            request.setAttribute("status", result.getStatus());
            return httpMessageContext.doNothing();
        }

        return httpMessageContext.notifyContainerAboutLogin(result);
    }
}
