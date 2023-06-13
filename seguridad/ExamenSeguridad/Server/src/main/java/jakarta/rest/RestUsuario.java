package jakarta.rest;

import common.ConstantsURL;
import dao.UsuarioDao;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.common.ConstantsAttributes;
import jakarta.inject.Inject;
import jakarta.keyconfig.KeyProvider;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import lombok.extern.log4j.Log4j2;
import model.User;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Path("users/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Log4j2
public class RestUsuario {
    @Context
    private HttpServletRequest request;
    @Context
    private SecurityContext securityContext;
    private final UsuarioDao dao;
    private final KeyProvider keyProvider;

    @Inject
    public RestUsuario(UsuarioDao dao, KeyProvider keyProvider) {
        this.dao = dao;
        this.keyProvider = keyProvider;
    }

    @GET
    @Path(ConstantsURL.PATH_LOGIN)
    public Response getValidatedUser(@QueryParam("nombre") String nombre, @QueryParam("password") String password) {
        User foundUser = dao.getUsuario(nombre);
        Set rolesAsignados = Set.of();
        if (!password.equals(foundUser.getPassword())) {
            throw new BadRequestException("Credenciales incorrectas");
        } else {
            List<String> roles = foundUser.getRoles();
            if (roles.contains("curioso")) {
                rolesAsignados = Set.of("raton", "curioso");
            } else if (roles.contains("biologo")) {
                rolesAsignados = Set.of("raton", "biologo");
            } else if (roles.contains("nivel2")) {
                rolesAsignados = Set.of("informe", "nivel2");
            } else if (roles.contains("nivel1")) {
                rolesAsignados = Set.of("informe", "nivel1");
            } else if (roles.contains("espia")) {
                rolesAsignados = Set.of("informe", "espia");
            }
        }
        String token = generateToken(nombre, rolesAsignados);
        return Response.status(Response.Status.OK).header(HttpHeaders.AUTHORIZATION, token).entity(foundUser).build();
    }

    public String generateToken(String userName, Set roles) {
        SecretKey codedKey = keyProvider.key();
        return Jwts.builder()
                .setExpiration(Date
                        .from(LocalDateTime.now()
                                .plusSeconds(10)
                                .atZone(ZoneId.systemDefault())
                                .toInstant()))
                .claim(ConstantsAttributes.ATTRIBUTE_USER, userName)
                .claim("roles", roles)
                .signWith(codedKey).compact();
    }

}
