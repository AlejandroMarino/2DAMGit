package ui.pantallas.login;

import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ui.common.BasePantallaController;

public class LoginController extends BasePantallaController {
    @FXML
    private TextField txUser;
    @FXML
    private PasswordField txPas;

    private final LoginViewModel loginViewModel;

    @Inject
    public LoginController(LoginViewModel loginViewModel) {
        this.loginViewModel = loginViewModel;
    }

    public void initialize() {
        loginViewModel.getState().addListener((observable, oldValue, newValue) -> {
            if (newValue.getError() != null) {
                getPrincipalController().error(newValue.getError());
            }
            if (newValue.isLoginOK()) {
                getPrincipalController().goNewspaper();
            }
        });
    }

    @FXML
    private void login() {
        String user = txUser.getText();
        String pas = txPas.getText();
        loginViewModel.login(user, pas);
    }
}
