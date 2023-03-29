package jakarta.rest;

import domain.modelo.Reader;
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

    private ServicesShop sR;

    @Inject
    public RestShops(ServicesShop sR) {
        this.sR = sR;
    }

    @GET
    public List<Reader> getAll() {
        return sR.getAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Reader get(@PathParam("id") int id) {
        return sR.get(id);
    }

    @POST
    public Response add(Reader reader) {
        Reader r = sR.add(reader);
        return Response.status(Response.Status.CREATED).entity(r).build();
    }

    @DELETE
    public Response delete(@QueryParam("id") int id) {
        sR.delete(id);
        return Response.noContent().build();
    }

    @PUT
    public Response update(Reader reader) {
        Reader r = sR.update(reader);
        return Response.status(Response.Status.OK).entity(r).build();
    }
}
