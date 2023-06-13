package jakarta.error;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import model.error.ApiError;
import model.exceptions.DataBaseException;

import java.time.LocalDateTime;

@Provider
public class DataBaseFailureExceptionMapper implements ExceptionMapper<DataBaseException> {
    public Response toResponse(DataBaseException exception) {
        ApiError apiError = new ApiError(exception.getMessage(), LocalDateTime.now());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(apiError)
                .type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}
