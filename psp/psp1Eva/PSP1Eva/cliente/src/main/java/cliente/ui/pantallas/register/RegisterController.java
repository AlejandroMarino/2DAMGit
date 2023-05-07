package cliente.ui.pantallas.register;

import cliente.common.Constants;
import cliente.ui.common.BasePantallaController;
import domain.models.User;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class RegisterController extends BasePantallaController {
    private final RegisterViewModel registerViewModel;

    @FXML
    private TextField textEmail;
    @FXML
    private TextField textPassword;
    @FXML
    private TextField textUsername;

    @Inject
    public RegisterController(RegisterViewModel registerViewModel) {
        this.registerViewModel = registerViewModel;
    }

    public void initialize() {
        registerViewModel.getState().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                if (newValue.getMessage() != null) {
                    getPrincipalController().message(newValue.getMessage());
                }
                if (newValue.isRegistered()) {
                    getPrincipalController().goLogin();
                }
            });
        });
    }

    @FXML
    private void register() {
        if (textEmail.getText().isEmpty() || textPassword.getText().isEmpty() || textUsername.getText().isEmpty()) {
            getPrincipalController().error(Constants.PLEASE_FILL_ALL_THE_FIELDS);
        } else {
            registerViewModel.register(new User(textUsername.getText(), textPassword.getText(), textEmail.getText()));
        }
    }

    @FXML
    private void login() {
        getPrincipalController().goLogin();
    }
}
