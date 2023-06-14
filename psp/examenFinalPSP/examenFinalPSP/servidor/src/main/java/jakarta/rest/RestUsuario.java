package jakarta.rest;

import domain.models.Usuario;
import domain.services.ServicesUsuario;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/usuario")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestUsuario {

    @Context
    private HttpServletResponse response;

    private final ServicesUsuario sU;

    @Inject
    public RestUsuario(ServicesUsuario sU) {
        this.sU = sU;
    }

    @POST
    @Path("/register")
    public Response register(Usuario usuario) {
        Usuario u = sU.register(usuario);
        return Response.status(Response.Status.CREATED).entity(u).build();
    }

    @POST
    @Path("/login")
    public Response login(Usuario usuario) {
        if (usuario.getNombre() == null || usuario.getPassword() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            Usuario u = sU.login(usuario);
            if (u == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            } else {
                String jwt = sU.generateJWS(usuario);
                response.setHeader(HttpHeaders.AUTHORIZATION, jwt);
                return Response.status(Response.Status.OK).build();
            }
        }
    }
}
