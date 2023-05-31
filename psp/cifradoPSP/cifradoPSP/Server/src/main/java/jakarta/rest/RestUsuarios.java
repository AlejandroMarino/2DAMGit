package jakarta.rest;

import domain.models.Usuario;
import domain.services.ServicesUsuario;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

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

    @GET
    public List<Usuario> getAll() {

    }
}
