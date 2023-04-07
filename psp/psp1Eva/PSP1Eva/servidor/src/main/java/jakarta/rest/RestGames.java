package jakarta.rest;

import domain.errors.ApiError;
import domain.models.Game;
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

    private final ServicesGame sG;

    @Inject
    public RestGames(ServicesGame sG) {
        this.sG = sG;}

    @GET
    public List<Game> getAll() {
        return sG.getAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Game get(@PathParam("id") int id) {
        return sG.get(id);
    }

    @GET
    @Path("/filter_name")
    public List<Game> filterByName(@QueryParam("name") String name) {
        return sG.filterByName(name);
    }

    @GET
    @Path("/filter_shop")
    public List<Game> getAllOfShop(@QueryParam("shop") int shopId) {
        return sG.getAllOfShop(shopId);
    }

    @POST
    public Response add(Game game) {
        if (sG.add(game)) {
            return Response.status(Response.Status.CREATED).entity(game).build();
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
    public Response update(Game game) {
        Game g = sG.update(game);
        return Response.status(Response.Status.OK).entity(g).build();
    }
}
