package jakarta.rest;

import domain.models.Shop;
import domain.servicios.ServicesShop;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/shops")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestShops {

    private ServicesShop sS;

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
    @Produces(MediaType.APPLICATION_JSON)
    public Shop get(@PathParam("id") int id) {
        return sS.get(id);
    }

    @POST
    public Response add(Shop shop) {
        Shop s = sS.add(shop);
        return Response.status(Response.Status.CREATED).entity(s).build();
    }

    @DELETE
    public Response delete(@QueryParam("id") int id) {
        sS.delete(id);
        return Response.noContent().build();
    }

    @PUT
    public Response update(Shop shop) {
        Shop s = sS.update(shop);
        return Response.status(Response.Status.OK).entity(s).build();
    }
}
