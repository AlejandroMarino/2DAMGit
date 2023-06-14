package ui.pantallas.sicario;

import domain.models.Contrato;
import domain.models.Estado;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ui.common.BasePantallaController;

public class SicarioController extends BasePantallaController {

    private final SicarioViewModel sicarioViewModel;

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

    @Inject
    public SicarioController(SicarioViewModel sicarioViewModel) {
        this.sicarioViewModel = sicarioViewModel;
    }

    public void initialize() {
        colHabilidad.setCellValueFactory(new PropertyValueFactory<>("habilidad"));
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        choiceEstado.getItems().setAll(Estado.values());

        sicarioViewModel.getState().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                if (newValue.getContratos() != null) {
                    tableContratos.getItems().clear();
                    tableContratos.getItems().setAll(newValue.getContratos());
                }
                if (newValue.getDetalle() != null) {
                    textHabilidad.setText(String.valueOf(newValue.getDetalle().getHabilidad()));
                    textPrecio.setText(String.valueOf(newValue.getDetalle().getPrecio()));
                    textObjetivo.setText(newValue.getDetalle().getObjetivo());
                    textTitulo.setText(newValue.getDetalle().getTitulo());
                }
                if (newValue.getEstado() != null) {
                    choiceEstado.setValue(newValue.getEstado());
                }
                if (newValue.getMessage() != null) {
                    getPrincipalController().error(newValue.getMessage());
                }
                getPrincipalController().root.setCursor(Cursor.DEFAULT);
            });
        });
        tableContratos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                sicarioViewModel.getDetalle(newSelection, getPrincipalController().getUsuarioActual(), getPrincipalController().getKeyStore());
                sicarioViewModel.getEstado(newSelection, getPrincipalController().getUsuarioActual());
            }
        });
    }

    @Override
    public void principalCargado() {
        clearFilters();
    }

    @FXML
    private void filtrarEstado() {
        getPrincipalController().root.setCursor(Cursor.WAIT);
        Estado e = choiceEstado.getValue();
        if (e != null) {
            sicarioViewModel.getContratosFilterEstado(getPrincipalController().getUsuarioActual(), e);
        }
    }

    @FXML
    private void actualizarEstado() {
        getPrincipalController().root.setCursor(Cursor.WAIT);
        Contrato c = tableContratos.getSelectionModel().getSelectedItem();
        Estado e = choiceEstado.getValue();
        if (c != null && e != null) {
            sicarioViewModel.updateEstado(getPrincipalController().getUsuarioActual(), c, e);
        }
    }

    @FXML
    private void clearFilters() {
        getPrincipalController().root.setCursor(Cursor.WAIT);
        sicarioViewModel.getContratos(getPrincipalController().getUsuarioActual());
    }
}
