package jakarta.rest;

import domain.models.Contrato;
import domain.models.Estado;
import domain.models.Usuario;
import domain.services.ServicesContratos;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/contratos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestContratos {

    private final ServicesContratos sC;

    @Inject
    public RestContratos(ServicesContratos sC) {
        this.sC = sC;
    }

    @POST
    public Response add(Contrato contrato) {
        if (sC.add(contrato) != null) {
            return Response.status(Response.Status.CREATED).entity(contrato).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    public Response update(Contrato contrato) {
        if (sC.update(contrato) != null) {
            return Response.status(Response.Status.OK).entity(contrato).build();
        } else {
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }
    }

    @GET
    @Path("/contratista")
    public List<Contrato> getContratosContratista(@QueryParam("id") int id) {
        return sC.getAllOfContratista(new Usuario(id));
    }

    @GET
    @Path("/sicario")
    public List<Contrato> getContratosSicario(@QueryParam("id") int id) {
        return sC.getAllOfSicario(new Usuario(id));
    }

    @GET
    @Path("/sicarioFilterEstado")
    public List<Contrato> getContratosSicarioFilterEstado(@QueryParam("id") int id, @QueryParam("estado") String estado) {
        return sC.getAllOfSicarioFilterEstado(new Usuario(id), Estado.valueOf(estado));
    }

    @GET
    @Path("/{id}")
    public Contrato get(@PathParam("id") int id) {
        return sC.get(id);
    }
}
