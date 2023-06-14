package jakarta.rest;

import domain.models.Serie;
import domain.services.ServicesSeries;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/series")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestSeries {

    private final ServicesSeries sS;

    @Inject
    public RestSeries(ServicesSeries sS) {
        this.sS = sS;
    }

    @GET
    public List<Serie> getAll() {
        return sS.getAll();
    }
}
