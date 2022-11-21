package jakarta.rest;

import domain.error.ApiError;
import domain.modelo.Newspaper;
import domain.servicios.ServicesNewspaper;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.List;

@Path("/newspapers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestNewspapers {

    private ServicesNewspaper sN;

    @Inject
    public RestNewspapers(ServicesNewspaper sN) {
        this.sN = sN;
    }

    @GET
    public List<Newspaper> getAllNewspapers() {
        return sN.getAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Newspaper getNewspaper(@PathParam("id") int id) {
        return sN.get(id);
    }

    @POST
    public Response addNewspaper(Newspaper newspaper) {
        if (sN.add(newspaper)) {
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
    public Response delNewspaper(@QueryParam("id") int id) {
        sN.delete(id);
        return Response.noContent().build();
    }

    @PUT
    public Response updateNewspaper(Newspaper newspaper) {
        Newspaper news = sN.update(newspaper);
        return Response.status(Response.Status.OK).entity(news).build();
    }
}
