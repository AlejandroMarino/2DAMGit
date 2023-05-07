package jakarta.rest;

import common.Constants;
import domain.errors.ApiError;
import domain.models.Game;
import domain.servicios.ServicesGame;
import jakarta.annotation.security.RolesAllowed;
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
    @RolesAllowed({Constants.ADMIN})
    public List<Game> getAll() {
        return sG.getAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Game get(@PathParam(Constants.ID) int id) {
        return sG.get(id);
    }

    @GET
    @Path("/filter_name")
    public List<Game> filterByName(@QueryParam(Constants.NAME) String name) {
        return sG.filterByName(name);
    }

    @GET
    @Path("/filter_shop")
    public List<Game> getAllOfShop(@QueryParam(Constants.SHOP) int shopId) {
        return sG.getAllOfShop(shopId);
    }

    @POST
    public Response add(Game game) {
        if (sG.add(game)) {
            return Response.status(Response.Status.CREATED).entity(game).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiError.builder()
                            .message(Constants.GAME_NOT_ADDED)
                            .fecha(LocalDateTime.now())
                            .build())
                    .build();
        }
    }

    @DELETE
    public Response delete(@QueryParam(Constants.ID) int id) {
        sG.delete(id);
        return Response.noContent().build();
    }

    @PUT
    public Game update(Game game) {
        return sG.update(game);
    }
}
