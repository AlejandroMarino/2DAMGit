package model.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import model.common.Constants;

@AllArgsConstructor
@Getter
public enum Error {
    ERROR(-1, Constants.ERROR),

    //EXCEPTIONS
    EXCEPTION_CONFLICT(409, Constants.WRONG_STATE),
    EXCEPTION_BAD_REQUEST(400, Constants.WRONG_REQUEST),
    EXCEPTION_UNAUTHORIZED(401, Constants.UNAUTHORIZED_REQUEST),
    EXCEPTION_FORBIDDEN(403, Constants.FORBIDDEN_REQUEST),
    EXCEPTION_NOT_FOUND(404, Constants.NOT_FOUND),
    EXCEPTION_TOKEN_EXPIRED(666, Constants.TOKEN_EXPIRED);


    private final int code;
    private final String description;
}
