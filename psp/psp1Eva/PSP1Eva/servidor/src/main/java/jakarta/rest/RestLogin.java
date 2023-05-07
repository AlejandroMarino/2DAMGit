package jakarta.rest;

import common.Constants;
import domain.models.User;
import domain.servicios.MandarMail;
import domain.servicios.ServicesLogin;
import jakarta.inject.Inject;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestLogin {

    @Context
    private HttpServletResponse response;

    private final MandarMail mandarMail;

    private final ServicesLogin sL;

    private final Utils utils;

    @Inject
    public RestLogin(MandarMail mandarMail, ServicesLogin sL, Utils utils) {
        this.mandarMail = mandarMail;
        this.sL = sL;
        this.utils = utils;
    }

    @POST
    @Path("/register")
    public Response register(User user) throws MessagingException {
        String bytes = utils.randomBytes();
        user.setActivationCode(bytes);
        User u = sL.register(user);
        if (u == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            mandarMail.generateAndSendEmail(user.getEmail(), "<html>generado <a href=\"http://localhost:8080/servidor-1.0-SNAPSHOT/warehouse/login/activation?code=" + bytes + "\" >marca</a> " + bytes + "</html>", Constants.WELCOME_TO_THE_GAMES_API);
            return Response.status(Response.Status.CREATED).entity(u).build();
        }
    }

    @GET
    public Response login(@QueryParam(Constants.USERNAME) String username, @QueryParam(Constants.PASSWORD) String password) {
        if (username == null || password == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            User user = sL.validationUser(username, password.toCharArray());
            if (user == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            } else {
                if (sL.activated(user.getUsername())) {
                    String jwt = sL.generateJWS(user);
                    response.setHeader(HttpHeaders.AUTHORIZATION, jwt);
                    return Response.status(Response.Status.OK).build();
                } else {
                    return Response.status(Response.Status.UNAUTHORIZED).build();
                }
            }
        }
    }

    @GET
    @Path("/activation")
    public Response validate(@QueryParam(Constants.CODE) String code) {
        if (code == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            boolean r = sL.validate(code);
            if (!r) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            } else {
                return Response.status(Response.Status.OK).build();
            }
        }
    }

}