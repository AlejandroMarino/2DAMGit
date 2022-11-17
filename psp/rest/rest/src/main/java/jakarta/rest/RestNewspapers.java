package jakarta.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import servicios.ServicesNewspaper;

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
    public Response getAllNewspapers() {
        return Response.ok(sN.getAll()).build();
    }
}
