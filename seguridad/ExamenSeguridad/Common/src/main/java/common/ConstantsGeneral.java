package common;

public class ConstantsGeneral {
    public static final String SPACE = " ";

    private ConstantsGeneral() {
    }

    public static final String REGISTRATION_COMPLETED = "Thank you for joining.\n" +
            "Check your email to activate your account.\n" +
            "The activation code will expire in " + ConstantsGeneral.ACTIVATION_TIME_LIMIT_MIN + " minutes.";

    public static final String NEW_ACTIVATION_CODE_SENT = "A new code was sent. Check your email, please.";
    public static final String PASSWORD_EMAIL_SENT = "You will receive an email.\n" +
            "Follow the indications to change the password, please.";

    public static final String ACTIVATION_CODE_EXPIRED = "The activation code has expired.\n" +
            "You can receive a new one by selecting the option in you app.";

    public static final int ACTIVATION_TIME_LIMIT_MIN = 5;
    public static final int TOKEN_TIME_LIMIT_SECS = 60;
}
