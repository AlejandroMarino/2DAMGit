package cliente.ui.pantallas.login;

import cliente.common.Constants;
import cliente.ui.common.BasePantallaController;
import domain.models.User;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginController extends BasePantallaController {
    @FXML
    private TextField textPassword;
    @FXML
    private TextField textUser;

    private final LoginViewModel loginViewModel;

    @Inject
    public LoginController(LoginViewModel loginViewModel) {
        this.loginViewModel = loginViewModel;
    }

    public void initialize() {
        loginViewModel.getState().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                if (newValue.getMessage() != null) {
                    getPrincipalController().message(newValue.getMessage());
                }
                if (newValue.isLogged()) {
                    getPrincipalController().goShops();
                }
            });
        });
    }

    @FXML
    private void login() {
        if (textUser.getText().isEmpty() || textPassword.getText().isEmpty()) {
            getPrincipalController().error(Constants.PLEASE_FILL_ALL_THE_FIELDS);
        } else {
            loginViewModel.login(new User(textUser.getText(), textPassword.getText()));
        }
    }

    @FXML
    private void register() {
        getPrincipalController().goRegister();
    }
}
