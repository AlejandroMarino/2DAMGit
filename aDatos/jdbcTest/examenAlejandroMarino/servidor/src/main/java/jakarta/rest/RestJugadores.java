package jakarta.rest;

import domain.modelo.Jugador;
import domain.servicios.ServiciosJugador;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/jugadores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestJugadores {

    private ServiciosJugador sJ;

    @Inject
    public RestJugadores(ServiciosJugador sR) {
        this.sJ = sR;
    }

    @GET
    public List<Jugador> getJugadoresEquipo(@QueryParam("nombre") String nombre) {
        return sJ.getJugadoresEquipo(nombre);
    }

    @POST
    public Response addJugador(@QueryParam("nombre") String nombre, Jugador jugador) {
        Jugador j = sJ.add(nombre, jugador);
        return Response.status(Response.Status.CREATED).entity(j).build();
    }

    @DELETE
    public Response delReader(@QueryParam("id") int id) {
        Jugador j = sJ.delete(id);
        return Response.status(Response.Status.ACCEPTED).entity(j).build();
    }

    @PUT
    public Response updateReader(Jugador jugador) {
        Jugador j = sJ.update(jugador);
        return Response.status(Response.Status.OK).entity(j).build();
    }
}
