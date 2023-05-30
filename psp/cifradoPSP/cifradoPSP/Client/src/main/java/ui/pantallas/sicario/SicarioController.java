package ui.pantallas.sicario;

import domain.models.Contrato;
import domain.models.Estado;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ui.common.BasePantallaController;

public class SicarioController extends BasePantallaController {


    @FXML
    private Label textHabilidad;
    @FXML
    private Label textPrecio;
    @FXML
    private Label textObjetivo;
    @FXML
    private Label textTitulo;
    @FXML
    private ComboBox<Estado> choiceEstado;
    @FXML
    private TableColumn<Contrato, Integer> colHabilidad;
    @FXML
    private TableColumn<Contrato, Integer> colId;
    @FXML
    private TableView<Contrato> tableContratos;

    @FXML
    private void filtrarEstado() {
    }

    @FXML
    private void actualizarEstado() {
    }

    @FXML
    private void clearFilters() {
    }
}
