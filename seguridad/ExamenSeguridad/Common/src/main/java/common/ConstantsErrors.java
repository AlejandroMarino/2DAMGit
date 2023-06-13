package common;

public class ConstantsErrors {
    public static final String INVALID_EMAIL_ADDRESS = "Invalid email address";

    private ConstantsErrors() {
    }

    public static final String ACTIVATE_ACCOUNT = "Activate your account to login";
    public static final String INVALID_LOGIN = "Wrong user name or password";
    public static final String NEWSPAPER_NOT_FOUND = "Newspaper not found";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String READER_NOT_FOUND = "Reader not found";
    public static final String LIST_EMPTY = "No elements matching the requirements were found";
    public static final String CONNECTION_TIMED_OUT = "The connection has timed out.";
    public static final String SERVER_CONNECTION = "Cannot connect to the server. Try again later.";
    public static final String SERVER_ERROR = "Database server is down. Please, try again later.";
    public static final String NOT_AUTHORIZED = "You are not authorized.";
    public static final String USER_EMAIL_REPEATED = "User name or email address is already being used";
    public static final String SELECT_FILTER = "Select one type or one newspaper";
    public static final String SELECT_NEWSPAPER = "Select a newspaper";
    public static final String CONTAINS_ARTICLES = "This newspaper contains articles.\nIf you delete it, the articles will be deleted as well.\nAre you sure you want to continue?";
    public static final String TOO_MANY_REQUESTS = "You reached the maximum amount\nof reader requests per minute.\nPlease, try again later.";
    public static final String ERROR_LOGOUT = "An error occurred while logging out.";
    public static final String ERROR_LOGIN = "Login to continue";
}
