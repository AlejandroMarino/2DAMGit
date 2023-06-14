package ui.pantallas.contratista;

import domain.models.Contrato;
import domain.models.Usuario;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ui.common.BasePantallaController;

public class ContratistaController extends BasePantallaController {

    private final ContratistaViewModel contratistaViewModel;

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

    @Inject
    public ContratistaController(ContratistaViewModel contratistaViewModel) {
        this.contratistaViewModel = contratistaViewModel;
    }

    public void initialize() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colHabilidadS.setCellValueFactory(new PropertyValueFactory<>("habilidad"));
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colHabilidadC.setCellValueFactory(new PropertyValueFactory<>("habilidad"));

        contratistaViewModel.getState().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                if (newValue.getContratos() != null) {
                    tableContratos.getItems().clear();
                    tableContratos.getItems().addAll(newValue.getContratos());
                }
                if (newValue.getSicarios() != null) {
                    tableSicarios.getItems().clear();
                    tableSicarios.getItems().addAll(newValue.getSicarios());
                }
                if (newValue.getDetalle() != null) {
                    textTitulo.setText(newValue.getDetalle().getTitulo());
                    textObjetivo.setText(newValue.getDetalle().getObjetivo());
                    textPrecio.setText(String.valueOf(newValue.getDetalle().getPrecio()));
                    textHabilidad.setText(String.valueOf(newValue.getDetalle().getHabilidad()));
                }
                if (newValue.getEstado() != null) {
                    textEstado.setText(String.valueOf(newValue.getEstado()));
                }
                if (newValue.getMessage() != null) {
                    getPrincipalController().message(newValue.getMessage());
                }
                getPrincipalController().root.setCursor(Cursor.DEFAULT);
            });
        });
        tableContratos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                contratistaViewModel.getSicariosOfContrato(newSelection);
                contratistaViewModel.getDetalle(newSelection, getPrincipalController().getKeyStore());
            }
        });
        tableSicarios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                getEstado(newSelection);
            }
        });
    }

    private void getEstado(Usuario usuario) {
        getPrincipalController().root.setCursor(Cursor.WAIT);
        Contrato c = tableContratos.getSelectionModel().getSelectedItem();
        if (c != null) {
            contratistaViewModel.getEstado(c, usuario);
        } else {
            contratistaViewModel.mostrarMensaje("Seleccione un contrato para ver el estado");
        }
    }

    @Override
    public void principalCargado() {
        getPrincipalController().root.setCursor(Cursor.WAIT);
        int idUsuario = getPrincipalController().getUsuarioActual().getId();
        contratistaViewModel.initialize(idUsuario);
    }

    @FXML
    private void crearContrato() {
        getPrincipalController().goCrearContrato();
    }
}
