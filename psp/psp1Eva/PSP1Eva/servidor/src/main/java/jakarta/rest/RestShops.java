package jakarta.rest;

import common.Constants;
import domain.errors.ApiError;
import domain.models.Shop;
import domain.servicios.ServicesShop;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.List;

@Path("/shops")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestShops {

    private final ServicesShop sS;

    @Inject
    public RestShops(ServicesShop sS) {
        this.sS = sS;
    }

    @GET
    public List<Shop> getAll() {
        return sS.getAll();
    }


    @GET
    @Path("/{id}")
    public Shop get(@PathParam("id") int id) {
        return sS.get(id);
    }

    @GET
    @Path("/filter")
    public List<Shop> filterByName(@QueryParam("name") String name) {
        return sS.filterByName(name);
    }

    @POST
    public Response add(Shop shop) {
        if (sS.add(shop)) {
            return Response.status(Response.Status.CREATED).entity(shop).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ApiError.builder()
                            .message(Constants.SHOP_NOT_ADDED)
                            .fecha(LocalDateTime.now())
                            .build())
                    .build();
        }
    }

    @DELETE
    public Response delete(@QueryParam("id") int id) {
        sS.delete(id);
        return Response.noContent().build();
    }

    @PUT
    public Shop update(Shop shop) {
        return sS.update(shop);
    }
}
