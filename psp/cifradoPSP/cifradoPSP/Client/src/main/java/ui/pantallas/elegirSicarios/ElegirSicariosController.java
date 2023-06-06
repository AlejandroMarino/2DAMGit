package ui.pantallas.elegirSicarios;

import domain.models.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ui.common.BasePantallaController;

public class ElegirSicariosController extends BasePantallaController {
    @FXML
    private TableView<Usuario> tableSicarios;
    @FXML
    private TableColumn<Usuario, String> colNombre;
    @FXML
    private TableColumn<Usuario, Integer> colHabilidad;

    @FXML
    private void confirmarSeleccion() {
    }
}
