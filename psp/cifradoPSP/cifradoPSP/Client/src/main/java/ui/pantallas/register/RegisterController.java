package ui.pantallas.register;

import common.Constants;
import domain.models.Tipo;
import domain.models.Usuario;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ui.common.BasePantallaController;

public class RegisterController extends BasePantallaController {
    private final RegisterViewModel registerViewModel;
    @FXML
    private Label habilidad;
    @FXML
    private TextField textHabilidad;
    @FXML
    private ComboBox<Tipo> comboTipo;
    @FXML
    private TextField textPassword;
    @FXML
    private TextField textUsername;

    @Inject
    public RegisterController(RegisterViewModel registerViewModel) {
        this.registerViewModel = registerViewModel;
    }

    public void initialize() {
        textHabilidad.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[1-9]|10")) {
                textHabilidad.setText(oldValue);
            }
        });
        comboTipo.getItems().addAll(Tipo.values());
        comboTipo.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(Tipo.CONTRATISTA)) {
                textHabilidad.setVisible(false);
                habilidad.setVisible(false);
            } else if (newValue.equals(Tipo.SICARIO)) {
                textHabilidad.setVisible(true);
                habilidad.setVisible(true);
            }
        });
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
        if (textHabilidad.getText().isEmpty() || textPassword.getText().isEmpty() || textUsername.getText().isEmpty()) {
            getPrincipalController().error(Constants.PLEASE_FILL_ALL_THE_FIELDS);
        } else {
            registerViewModel.register(new Usuario(textUsername.getText(), comboTipo.getValue(), textPassword.getText(), Integer.parseInt(textHabilidad.getText())));
        }
    }

    @FXML
    private void login() {
        getPrincipalController().goLogin();
    }

    @FXML
    private void checkTipe() {
        if (comboTipo.getValue().equals(Tipo.SICARIO)) {
            habilidad.setVisible(true);
            textHabilidad.setVisible(true);
        } else {
            habilidad.setVisible(false);
            textHabilidad.setVisible(false);
        }
    }
}