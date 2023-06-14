package ui.pantallas.elegirSicarios;

import domain.models.Usuario;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ui.common.BasePantallaController;

public class ElegirSicariosController extends BasePantallaController {

    private final ElegirSicariosViewModel elegirSicariosViewModel;

    @FXML
    private TableView<Usuario> tableSicarios;

    @FXML
    private TableColumn<Usuario, String> colNombre;
    @FXML
    private TableColumn<Usuario, Integer> colHabilidad;

    @Inject
    public ElegirSicariosController(ElegirSicariosViewModel elegirSicariosViewModel) {
        this.elegirSicariosViewModel = elegirSicariosViewModel;
    }

    public void initialize() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colHabilidad.setCellValueFactory(new PropertyValueFactory<>("habilidad"));

        elegirSicariosViewModel.getState().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                if (newValue.getSicarios() != null) {
                    tableSicarios.getItems().clear();
                    tableSicarios.getItems().setAll(newValue.getSicarios());
                }
                if (newValue.getMessage() != null) {
                    getPrincipalController().error(newValue.getMessage());
                }
                if (newValue.isAniadidos()) {
                    getPrincipalController().volverContratista();
                }
                getPrincipalController().root.setCursor(Cursor.DEFAULT);
            });
        });
    }

    @Override
    public void principalCargado() {
        getPrincipalController().root.setCursor(Cursor.WAIT);
        elegirSicariosViewModel.initialize(getPrincipalController().getContratoActual());
    }

    @FXML
    private void confirmarSeleccion() {
        getPrincipalController().root.setCursor(Cursor.WAIT);
        if (tableSicarios.getSelectionModel().getSelectedItems().isEmpty()) {
            elegirSicariosViewModel.mostrarMensaje("Debe seleccionar al menos un sicario");
        } else {
            elegirSicariosViewModel.elegirSicarios(tableSicarios.getSelectionModel().getSelectedItems(),
                    getPrincipalController().getContratoActual(), getPrincipalController().getUsuarioActual(), getPrincipalController().getKeyStore());
        }
    }
}
