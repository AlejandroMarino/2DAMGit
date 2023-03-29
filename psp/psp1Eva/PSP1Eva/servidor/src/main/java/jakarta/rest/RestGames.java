package jakarta.rest;

import domain.error.ApiError;
import domain.modelo.Newspaper;
import domain.servicios.ServicesGame;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.List;

@Path("/games")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestGames {

    private ServicesGame sG;

    @Inject
    public RestGames(ServicesGame sG) {
        this.sG = RestGames.this.sG;
    }

    @GET
    public List<Newspaper> getAll() {
        return sG.getAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Newspaper get(@PathParam("id") int id) {
        return sG.get(id);
    }

    @POST
    public Response add(Newspaper newspaper) {
        if (sG.add(newspaper)) {
            return Response.status(Response.Status.CREATED).entity(newspaper).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiError.builder()
                            .message("newspaper no añadido")
                            .fecha(LocalDateTime.now())
                            .build())
                    .build();
        }
    }

    @DELETE
    public Response delete(@QueryParam("id") int id) {
        sG.delete(id);
        return Response.noContent().build();
    }

    @PUT
    public Response update(Newspaper newspaper) {
        Newspaper news = sG.update(newspaper);
        return Response.status(Response.Status.OK).entity(news).build();
    }
}
