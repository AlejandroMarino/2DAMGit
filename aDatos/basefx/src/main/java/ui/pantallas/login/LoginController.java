package ui.pantallas.login;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import ui.common.BasePantallaController;

public class LoginController extends BasePantallaController {
    public void login(ActionEvent actionEvent) {
    }

    private final LoginViewModel loginViewModel;

    @Inject
    public LoginController(LoginViewModel loginViewModel) {
        this.loginViewModel = loginViewModel;
    }

    public void initialize() {
        loginViewModel.getState().addListener((observable, oldValue, newValue) -> {
            if (newValue.getError() != null && newValue.isLoginOK()) {
                getPrincipalController().goRegistro(textDNI.getText());
            } else if (newValue.getError() != null) {
                getPrincipalController().error(newValue.getError());
            } else if (newValue.isLoginOK() && textDNI.getText().equalsIgnoreCase("admin")) {
                getPrincipalController().goAdmin();
            } else {
                getPrincipalController().goCliente(loginViewModel.getUsuario(textDNI.getText()));
            }
        });
    }

    @Override
    public void principalCargado() {
        if (getPrincipalController().getActualUser() != null) {
            textDNI.setText(getPrincipalController().getActualUser().getDni());
        }
    }

    @FXML
    private void continuar(ActionEvent actionEvent) {
        String dni = textDNI.getText();
        loginViewModel.login(dni);
    }
}
