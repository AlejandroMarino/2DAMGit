package jakarta.errores;

import domain.errors.ApiError;
import domain.model.errores.BadRequestException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> {
    public Response toResponse(BadRequestException exception) {
        ApiError apiError = new ApiError(exception.getMessage());
        return Response.status(Response.Status.BAD_REQUEST).entity(apiError)
                .type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}
