package jakarta.rest;

import domain.models.Usuario;
import domain.services.ServicesUsuario;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestUsuarios {

    private final ServicesUsuario sU;

    @Inject
    public RestUsuarios(ServicesUsuario sU) {
        this.sU = sU;
    }

    @POST
    @Path("/register")
    public Response register(Usuario usuario) {
        if (sU.register(usuario) != null) {
            return Response.status(Response.Status.CREATED).entity(usuario).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("/login")
    public Response login(Usuario usuario) {
        Usuario u = sU.login(usuario);
        if (u != null) {
            return Response.status(Response.Status.OK).entity(u).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/sicarioContrato")
    public List<Usuario> getSicariosContrato(@QueryParam("contrato") int idContrato) {
        return sU.getSicariosContrato(idContrato);
    }

    @GET
    @Path("/sicariosFilterHabilidad")
    public List<Usuario> getSicariosFilterHabilidad(@QueryParam("habilidad") int habilidad) {
        return sU.getSicariosFilterHabilidad(habilidad);
    }


}
