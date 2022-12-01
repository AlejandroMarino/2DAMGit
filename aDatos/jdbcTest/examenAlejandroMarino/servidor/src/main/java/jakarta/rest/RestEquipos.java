package jakarta.rest;

import domain.modelo.Equipo;
import domain.servicios.ServiciosEquipo;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/equipos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestEquipos {

    private ServiciosEquipo sE;

    @Inject
    public RestEquipos(ServiciosEquipo sN) {
        this.sE = sN;
    }

    @GET
    public List<Equipo> getAllEquipos() {
        return sE.getAll();
    }

    @GET
    @Path("/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public Equipo getEquipo(@PathParam("nombre") String nombre) {
        return sE.get(nombre);
    }

    @POST
    public Response addEquipo(Equipo equipo) {
        Equipo e = sE.add(equipo);
        return Response.status(Response.Status.CREATED).entity(e).build();
    }
}
