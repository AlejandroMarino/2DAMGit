package jakarta.rest;

import domain.models.Personaje;
import domain.services.ServicesPersonaje;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/personajes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestPersonaje {

    private final ServicesPersonaje sP;

    @Inject
    public RestPersonaje(ServicesPersonaje sP) {
        this.sP = sP;
    }

    @GET
    @RolesAllowed({"ARMAS"})
    public List<Personaje> getPersonajesArmaFilterHabilidad(@QueryParam("arma") int armaId,@QueryParam("habilidad") int habilidad) {
        return sP.getPersonajesArmaFilterHabilidad(armaId, habilidad);
    }
}
