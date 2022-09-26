package ui.pantallas.login;

import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import ui.common.BasePantallaController;

public class LoginController extends BasePantallaController {
    @FXML
    private void login(ActionEvent actionEvent) {
    }

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
        });
    }

    @Override
    public void principalCargado() {

    }

    @FXML
    private void continuar(ActionEvent actionEvent) {

    }
}
