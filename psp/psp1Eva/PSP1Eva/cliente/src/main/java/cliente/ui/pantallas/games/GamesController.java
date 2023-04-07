package cliente.ui.pantallas.games;

import cliente.ui.common.BasePantallaController;
import domain.models.Game;
import domain.models.Shop;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
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
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        gamesViewModel.getState().addListener((observable, oldValue, newValue) -> {
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
        String name = textName.getText();
        if (name != null && !name.isBlank()) {
            Shop shop = choiceShops.getValue();
            if (shop != null) {
                LocalDate date = datePicker.getValue();
                String description = textDescription.getText();
                gamesViewModel.add(new Game(name, description, date, shop.getId()));
            } else {
                getPrincipalController().error("Select a shop");
            }
        } else {
            getPrincipalController().error("Write a name");
        }
    }

    @FXML
    private void delete() {
        Game game = tableGames.getSelectionModel().getSelectedItem();
        if (game != null) {
            gamesViewModel.delete(game.getId());
        } else {
            getPrincipalController().error("Select a game");
        }
    }

    @FXML
    private void update() {
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
                    getPrincipalController().error("Select a shop for the game");
                }
            } else {
                getPrincipalController().error("Write a name");
            }
        } else {
            getPrincipalController().error("Select a game to update");
        }
    }

    @FXML
    private void searchByName() {
        String text = textSearch.getText();
        if (text != null && !text.isBlank()) {
            gamesViewModel.search(text);
        } else {
            getPrincipalController().error("Write something to search");
        }
    }

    @FXML
    private void filterByShop() {
        Shop shop = choiceShops.getValue();
        if (shop != null) {
            gamesViewModel.filterByShop(shop.getId());
        } else {
            getPrincipalController().error("Select a shop");
        }
    }

    @FXML
    private void clearFilters() {
        gamesViewModel.getAllGames();
    }
}
