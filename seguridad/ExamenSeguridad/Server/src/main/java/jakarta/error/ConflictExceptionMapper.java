package jakarta.error;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import model.error.ApiError;
import model.exceptions.ConflictException;

import java.time.LocalDateTime;

@Provider
public class ConflictExceptionMapper implements ExceptionMapper<ConflictException> {
    public Response toResponse(ConflictException exception) {
        ApiError apiError = new ApiError(exception.getMessage(), LocalDateTime.now());
        return Response.status(Response.Status.CONFLICT).entity(apiError)
                .type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}
