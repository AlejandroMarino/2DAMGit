package jakarta.rest;

import common.ConstantsURL;
import dao.InformesDao;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import lombok.extern.log4j.Log4j2;
import model.Informe;

import java.util.List;

@Path("informes/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Log4j2
@RolesAllowed("informe")
public class RestInforme {
    @Context
    private HttpServletRequest request;
    @Context
    private SecurityContext securityContext;
    private final InformesDao dao;

    @Inject
    public RestInforme(InformesDao dao) {
        this.dao = dao;
    }


    @GET
    public List<Informe> getInformes() {
        String rol = null;
        if (securityContext.isUserInRole("nivel2")) {
            rol = "nivel2";
        } else {
            rol = "nivel1";
        }
        return dao.getInformes(rol);
    }

    @GET
    @Path(ConstantsURL.PATH_ID)
    public Informe getInforme(@PathParam(ConstantsURL.PARAM_ID) int id) {
        String rol;
        if (securityContext.isUserInRole("nivel2")) {
            rol = "nivel2";
        } else {
            rol = "nivel1";
        }
        return dao.getInforme(id, rol);
    }

    @POST
    @RolesAllowed("espia")
    public Response addInforme(Informe informe) {
        dao.addInforme(informe);
        return Response.status(Response.Status.CREATED).entity(informe).build();
    }

}
