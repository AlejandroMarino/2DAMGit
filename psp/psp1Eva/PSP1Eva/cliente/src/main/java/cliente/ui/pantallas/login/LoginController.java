package cliente.ui.pantallas.login;

import cliente.ui.common.BasePantallaController;
import jakarta.inject.Inject;
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

    private void initialize() {
        loginViewModel.getState().addListener((observable, oldValue, newValue) -> {
            if (newValue.getMessage() != null) {
                getPrincipalController().error(newValue.getMessage());
            }
        });
    }

    @FXML
    private void login(ActionEvent actionEvent) {

    }

    @FXML
    private void register(ActionEvent actionEvent) {
        getPrincipalController().goRegister();
    }
}
