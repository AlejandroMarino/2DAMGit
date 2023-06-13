package common;

public class ConstantsURL {
    private ConstantsURL() {
    }

    public static final String SLASH = "/";

    //PATH
    public static final String PATH_LOGIN = "login";
    public static final String PATH_LOGOUT = "logout";
    public static final String PATH_USERS = "users/";
    public static final String PATH_FOLDERS = "folders/";
    public static final String PATH_MESSAGES = "messages/";
    public static final String PATH_CERTIFICATE = "certificate/";
    public static final String PATH_FOLDER_WITH_MESSAGES = "folder-and-messages/";
    public static final String PATH_FOLDER_NAME = "by-folder-name/";
    public static final String PATH_SERVER_PK = "server_pk/";
    public static final String PATH_API = "/api/";
    public static final String PATH_ID = "{id}";
    public static final String PATH_FOLDER_ID = "{folder-id}";

    //PARAM
    public static final String PARAM_ID = "id";
    public static final String PARAM_USER_NAME = "user";
    public static final String PARAM_FOLDER_NAME = "folder";
    public static final String PARAM_FOLDER_ID = "folder-id";
    public static final String PARAM_KEY = "key";
    public static final String PARAM_ENCRYPTED_PUBLIC_KEY = "encryptedPublicKey";
    public static final String PARAM_ENCRYPTED_PASSWORD = "encryptedPassword";
    public static final String PARAM_PRIVATE_KEY = "privateKey";
    public static final String PATH_ACTIVATION = "/webstore/Activation";
    public static final String PATH_PASSWORD_CHANGE = "/webstore/Password-change";
}
