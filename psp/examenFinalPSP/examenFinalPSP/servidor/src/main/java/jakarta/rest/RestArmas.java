package jakarta.rest;

import common.Constants;
import domain.models.Arma;
import domain.services.ServicesArma;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/armas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestArmas {

    private final ServicesArma sA;

    @Inject
    public RestArmas(ServicesArma sA) {
        this.sA = sA;
    }

    @POST
    @RolesAllowed({"ARMAS"})
    public Response addArma(Arma arma) {
        sA.addArma(arma);
        return Response.ok().entity(arma).build();
    }

    @PUT
    @RolesAllowed({"ARMAS"})
    public Response updateArma(Arma arma) {
        sA.update(arma);
        return Response.ok().entity(arma).build();
    }

    @GET
    @RolesAllowed({"ARMAS"})
    public List<Arma> getAll() {
        return sA.getAll();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"ARMAS"})
    public Response deleteArmaSinRelaciones(@PathParam("id") int id) {
        sA.deleteArmaSinRelaciones(id);
        return Response.noContent().build();
    }

    @DELETE
    @RolesAllowed({"ARMAS"})
    public Response deleteArmaConRelaciones(@QueryParam("id") int id) {
        sA.deleteArmaConRelaciones(id);
        return Response.noContent().build();
    }
}