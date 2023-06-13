package jakarta.security;

import common.ConstantsErrors;
import common.ConstantsGeneral;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.common.Constants;
import jakarta.common.ConstantsAttributes;
import jakarta.common.ConstantsRoles;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.keyconfig.KeyProvider;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.credential.BasicAuthenticationCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.HttpHeaders;
import lombok.extern.log4j.Log4j2;
import model.error.Error;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@ApplicationScoped
@Log4j2
public class JWTAuth implements HttpAuthenticationMechanism {
    private final KeyProvider keyProvider;

    @Inject
    private BasicIdentityStore identity;

    @Inject
    public JWTAuth(KeyProvider keyProvider) {
        this.keyProvider = keyProvider;
    }

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse,
                                                HttpMessageContext httpMessageContext) {
        CredentialValidationResult c = CredentialValidationResult.INVALID_RESULT;
        String authHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null) {
            String[] values = authHeader.split(ConstantsGeneral.SPACE);
            String header = values[1];
            if (values[0].equalsIgnoreCase(Constants.AUTH_BASIC)) {
                c = verifyUser(httpServletResponse, header);
            } else if (values[0].equalsIgnoreCase(Constants.AUTH_BEARER)) {
                c = verifyToken(header);
                if (c.getStatus().equals(CredentialValidationResult.Status.NOT_VALIDATED)) {
                    httpServletResponse.setStatus(Error.EXCEPTION_TOKEN_EXPIRED.getCode());
                    httpMessageContext.setResponse(httpServletResponse);
                    return AuthenticationStatus.SEND_FAILURE;
                }
            }
        }
        return sendResult(httpServletRequest, httpMessageContext, c);
    }

    @Override
    public void cleanSubject(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) {
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    private AuthenticationStatus sendResult(HttpServletRequest request
            , HttpMessageContext context
            , CredentialValidationResult c) {
        if (c.getStatus().equals(CredentialValidationResult.Status.INVALID)) {
            request.getSession().setAttribute(ConstantsAttributes.ATTRIBUTE_ERROR_LOGIN, ConstantsErrors.INVALID_LOGIN);
            return context.doNothing();
        } else if (c.getStatus().equals(CredentialValidationResult.Status.NOT_VALIDATED)) {
            request.getSession().setAttribute(ConstantsAttributes.ATTRIBUTE_ERROR_ACTIVATION, ConstantsErrors.ACTIVATE_ACCOUNT);
            return context.doNothing();
        } else {
            return context.notifyContainerAboutLogin(c);
        }
    }

    private CredentialValidationResult verifyUser(HttpServletResponse response, String header) {
        BasicAuthenticationCredential user = new BasicAuthenticationCredential(header);
        return identity.validate(user);
    }

    public CredentialValidationResult verifyToken(String header) {
        CredentialValidationResult c;
        SecretKey key = keyProvider.key();

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(header)
                    .getBody();
            String userName = claims.get(ConstantsAttributes.ATTRIBUTE_USER, String.class);
            ArrayList roles = claims.get("roles", ArrayList.class);
            Set rolSet = new HashSet<>();
            roles.forEach(rolSet::add);
            c = new CredentialValidationResult(userName, rolSet);
        } catch (JwtException e) {
            log.error(e.getMessage(), e);
            c = CredentialValidationResult.INVALID_RESULT;

            if (e instanceof ExpiredJwtException) {
                c = CredentialValidationResult.NOT_VALIDATED_RESULT;
            }
        }

        return c;
    }
}
