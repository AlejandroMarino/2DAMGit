package jakarta.rest;

import domain.models.Contrato;
import domain.models.SicarioContrato;
import domain.models.Usuario;
import domain.services.ServicesSicariosContratos;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/sicarios_contratos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestSicariosContratos {

    private final ServicesSicariosContratos sSC;

    @Inject
    public RestSicariosContratos(ServicesSicariosContratos sSC) {
        this.sSC = sSC;
    }

    @POST
    public Response add(List<SicarioContrato> sicariosContratos) {
        sSC.add(sicariosContratos);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    public Response update(SicarioContrato sicarioContrato) {
        if (sSC.update(sicarioContrato) != null) {
            return Response.status(Response.Status.OK).entity(sicarioContrato).build();
        } else {
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }
    }

    @GET
    public SicarioContrato get(@QueryParam("sicario") int idSicario, @QueryParam("contrato") int idContrato) {
        return sSC.get(new SicarioContrato(new Usuario(idSicario), new Contrato(idContrato)));
    }
}
