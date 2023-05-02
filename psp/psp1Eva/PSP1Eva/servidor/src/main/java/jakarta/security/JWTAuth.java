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
import jakarta.security.enterprise.credential.BasicAuthenticationCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.HttpHeaders;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class JWTAuth implements HttpAuthenticationMechanism {
    @Inject
    private MyIdentityStore identityStore;
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) throws AuthenticationException {
        CredentialValidationResult result = CredentialValidationResult.INVALID_RESULT;
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null) {
            String[] parts = header.split(" ");
            if (parts[0].equalsIgnoreCase("Basic")) {
                result = identityStore.validate(new BasicAuthenticationCredential(parts[1]));
                if (result.getStatus() == CredentialValidationResult.Status.VALID) {
                    String jws = Jwts.builder()
                            .setSubject("servidor")
                            .setIssuer("me")
                            .setExpiration(Date.from(LocalDateTime.now().plusMinutes(15).atZone(ZoneId.systemDefault()).toInstant()))
                            .claim("user", result.getCallerPrincipal().getName())
                            .claim("roles", result.getCallerGroups())
                            .signWith(key).compact();
                    response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jws);
                }

            } else if (parts[0].equalsIgnoreCase("Bearer")) {
                Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(parts[1]);
                List<String> roles = jws.getBody().get("roles", List.class);
                Set<String> rolesSet = new HashSet<>(roles);
                String user = jws.getBody().get("user", String.class);
                result = new CredentialValidationResult(user, rolesSet);

            } else if (parts[0].equalsIgnoreCase("Logout")) {
                result = CredentialValidationResult.NOT_VALIDATED_RESULT;
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }

        } else {
            result = CredentialValidationResult.NOT_VALIDATED_RESULT;

        }
        if (!result.getStatus().equals(CredentialValidationResult.Status.VALID)) {
            return httpMessageContext.doNothing();
        }
        return httpMessageContext.notifyContainerAboutLogin(result);
    }
}
