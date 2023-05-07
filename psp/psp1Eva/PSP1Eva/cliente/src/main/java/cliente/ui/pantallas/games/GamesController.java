package cliente.ui.pantallas.games;

import cliente.common.Constants;
import cliente.ui.common.BasePantallaController;
import domain.models.Game;
import domain.models.Shop;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

public class GamesController extends BasePantallaController {

    private final GamesViewModel gamesViewModel;
    @FXML
    private TableView<Game> tableGames;
    @FXML
    private TableColumn<String, Game> colName;
    @FXML
    private TableColumn<String, Game> colDate;
    @FXML
    private TableColumn<String, Game> colDescription;
    @FXML
    private TextField textName;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextArea textDescription;
    @FXML
    private ComboBox<Shop> choiceShops;
    @FXML
    private TextField textSearch;

    @Inject
    public GamesController(GamesViewModel gamesViewModel) {
        this.gamesViewModel = gamesViewModel;
    }

    public void initialize() {
        colName.setCellValueFactory(new PropertyValueFactory<>(Constants.NAME));
        colDate.setCellValueFactory(new PropertyValueFactory<>(Constants.RELEASE_DATE));
        colDescription.setCellValueFactory(new PropertyValueFactory<>(Constants.DESCRIPTION));

        gamesViewModel.getState().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                if (newValue.getGames() != null) {
                    tableGames.getItems().clear();
                    tableGames.getItems().setAll(newValue.getGames());
                }
                if (newValue.getError() != null) {
                    getPrincipalController().error(newValue.getError());
                }
                if (newValue.getShops() != null) {
                    choiceShops.getItems().clear();
                    choiceShops.getItems().setAll(newValue.getShops());
                }
                if (newValue.getShop() != null) {
                    choiceShops.setValue(newValue.getShop());
                }
                getPrincipalController().root.setCursor(Cursor.DEFAULT);
            });
        });
        tableGames.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                textName.setText(newValue.getName());
                datePicker.setValue(newValue.getReleaseDate());
                textDescription.setText(newValue.getDescription());
                gamesViewModel.getShop(newValue.getShopId());
            }
        });
    }

    @Override
    public void principalCargado() {
        gamesViewModel.initialize();
    }

    @FXML
    private void add() {
        getPrincipalController().root.setCursor(Cursor.WAIT);
        String name = textName.getText();
        if (name != null && !name.isBlank()) {
            Shop shop = choiceShops.getValue();
            if (shop != null) {
                LocalDate date = datePicker.getValue();
                String description = textDescription.getText();
                gamesViewModel.add(new Game(name, description, date, shop.getId()));
            } else {
                mostrarError(Constants.SELECT_A_SHOP);
            }
        } else {
            mostrarError(Constants.WRITE_A_NAME);
        }
    }

    @FXML
    private void delete() {
        getPrincipalController().root.setCursor(Cursor.WAIT);
        Game game = tableGames.getSelectionModel().getSelectedItem();
        if (game != null) {
            gamesViewModel.delete(game.getId());
        } else {
            mostrarError(Constants.SELECT_A_GAME);
        }
    }

    @FXML
    private void update() {
        getPrincipalController().root.setCursor(Cursor.WAIT);
        Game game = tableGames.getSelectionModel().getSelectedItem();
        if (game != null) {
            String name = textName.getText();
            if (name != null && !name.isBlank()) {
                Shop shop = choiceShops.getValue();
                if (shop != null) {
                    LocalDate date = datePicker.getValue();
                    String description = textDescription.getText();
                    gamesViewModel.update(new Game(game.getId(), name, description, date, shop.getId()));
                } else {
                    mostrarError(Constants.SELECT_A_SHOP_FOR_THE_GAME);
                }
            } else {
                mostrarError(Constants.WRITE_A_NAME);
            }
        } else {
            mostrarError(Constants.SELECT_A_GAME_TO_UPDATE);
        }
    }

    @FXML
    private void searchByName() {
        getPrincipalController().root.setCursor(Cursor.WAIT);
        String text = textSearch.getText();
        if (text != null && !text.isBlank()) {
            gamesViewModel.search(text);
        } else {
            mostrarError(Constants.WRITE_SOMETHING_TO_SEARCH);
        }
    }

    @FXML
    private void filterByShop() {
        getPrincipalController().root.setCursor(Cursor.WAIT);
        Shop shop = choiceShops.getValue();
        if (shop != null) {
            gamesViewModel.filterByShop(shop.getId());
        } else {
            mostrarError(Constants.SELECT_A_SHOP);
        }
    }

    @FXML
    private void clearFilters() {
        getPrincipalController().root.setCursor(Cursor.WAIT);
        gamesViewModel.getAllGames();
    }

    private void mostrarError(String error) {
        getPrincipalController().error(error);
        getPrincipalController().root.setCursor(Cursor.DEFAULT);
    }
}
