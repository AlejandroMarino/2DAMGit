package jakarta.rest;

import domain.modelo.Reader;
import domain.servicios.ServicesReader;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/readers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestReaders {

    private ServicesReader sR;

    @Inject
    public RestReaders(ServicesReader sR) {
        this.sR = sR;
    }

    @GET
    public List<Reader> getReaders() {
        return sR.getAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Reader getReader(@PathParam("id") int id) {
        return sR.get(id);
    }

    @POST
    public Response addReader(Reader reader) {
        Reader r = sR.add(reader);
        return Response.status(Response.Status.CREATED).entity(r).build();
    }

    @DELETE
    public Response delReader(@QueryParam("id") int id) {
        sR.delete(id);
        return Response.noContent().build();
    }

    @PUT
    public Response updateReader(Reader reader) {
        Reader r = sR.update(reader);
        return Response.status(Response.Status.OK).entity(r).build();
    }
}
