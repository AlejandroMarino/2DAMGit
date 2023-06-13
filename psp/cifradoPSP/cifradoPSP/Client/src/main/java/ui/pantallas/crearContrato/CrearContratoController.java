package ui.pantallas.crearContrato;

import domain.model.Detalle;
import domain.models.Contrato;
import domain.models.Usuario;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ui.common.BasePantallaController;

public class CrearContratoController extends BasePantallaController {

    private final CrearContratoViewModel crearContratoViewModel;

    @FXML
    private TextField textTitulo;

    @FXML
    private TextArea textObjetivo;
    @FXML
    private TextField textPrecio;
    @FXML
    private TextField textHabilidad;

    @Inject
    public CrearContratoController(CrearContratoViewModel crearContratoViewModel) {
        this.crearContratoViewModel = crearContratoViewModel;
    }

    public void initialize() {
        crearContratoViewModel.getState().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                if (newValue.getMessage() != null) {
                    crearContratoViewModel.mostrarMensaje(newValue.getMessage());
                }
                if (newValue.getContrato() != null) {
                    crearContratoViewModel.mostrarMensaje("Contrato creado con exito");
                    siguientePantalla();
                }
                getPrincipalController().root.setCursor(Cursor.DEFAULT);
            });
        });
    }

    private void siguientePantalla() {
        getPrincipalController().goElegirSicarios(crearContratoViewModel.getState().get().getContrato());
    }

    @FXML
    private void elegirSicarios() {
        getPrincipalController().root.setCursor(Cursor.WAIT);
        Usuario usuario = getPrincipalController().getUsuarioActual();
        if (textTitulo.getText().isBlank() || textObjetivo.getText().isBlank() || textPrecio.getText().isBlank() || textHabilidad.getText().isBlank()) {
            crearContratoViewModel.mostrarMensaje("Debe rellenar todos los campos");
        } else {
            Detalle detalle = new Detalle(textTitulo.getText(), textObjetivo.getText(), Double.parseDouble(textPrecio.getText()), Integer.parseInt(textHabilidad.getText()));
            crearContratoViewModel.crearContrato(new Contrato(usuario, Integer.parseInt(textHabilidad.getText())), detalle);
        }
    }
}
