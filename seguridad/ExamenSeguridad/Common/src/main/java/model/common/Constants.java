package model.common;

public class Constants {
    private Constants() {
    }

    public static final String ERROR = "ERROR";

    //SQL EXCEPTIONS
    public static final String THE_OBJECT_APPEARS_IN_MORE_TABLES = "Key appears in other tables.";
    public static final String NON_EXISTENT_KEY = "Key doesn't exist.";
    public static final String REPEATED_KEY_SAVED = "Duplicated entry for key.";
    public static final String WRONG_STATE = "Wrong state";
    public static final String WRONG_REQUEST = "Wrong request";
    public static final String UNAUTHORIZED_REQUEST = "Unauthorized request";
    public static final String FORBIDDEN_REQUEST = "Forbidden request";
    public static final String NOT_FOUND = "Not found";
    public static final String TOKEN_EXPIRED = "Token expired";
}
