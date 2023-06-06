package jakarta.errores;

import domain.errors.ApiError;
import domain.model.errores.DataModificationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.LocalDateTime;

@Provider
public class DataModificationExceptionMapper implements ExceptionMapper<DataModificationException> {

    public Response toResponse(DataModificationException exception) {
        ApiError apiError = new ApiError(exception.getMessage(), LocalDateTime.now());
        return Response.status(Response.Status.NOT_MODIFIED).entity(apiError)
                .type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}
