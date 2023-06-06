package ui.pantallas.contratista;

import domain.models.Contrato;
import domain.models.Tipo;
import domain.models.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ui.common.BasePantallaController;

public class ContratistaController extends BasePantallaController {

    @FXML
    private Label textEstado;
    @FXML
    private TableColumn<Usuario, String> colNombre;
    @FXML
    private TableColumn<Usuario, Integer> colHabilidadS;
    @FXML
    private TableView<Usuario> tableSicarios;
    @FXML
    private TableView<Contrato> tableContratos;
    @FXML
    private TableColumn<Contrato, String> colId;
    @FXML
    private TableColumn<Contrato, Integer> colHabilidadC;
    @FXML
    private Label textTitulo;
    @FXML
    private Label textObjetivo;
    @FXML
    private Label textPrecio;
    @FXML
    private Label textHabilidad;

    @FXML
    private void crearContrato() {

    }
}
