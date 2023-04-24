package cliente.ui.pantallas.shops;

import cliente.common.Constants;
import cliente.ui.common.BasePantallaController;
import domain.models.Shop;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ShopsController extends BasePantallaController {
    private final ShopsViewModel shopsViewModel;

    @FXML
    private ListView<Shop> listShops;
    @FXML
    private TextField textSearch;
    @FXML
    private TextField textName;

    @Inject
    public ShopsController(ShopsViewModel shopsViewModel) {
        this.shopsViewModel = shopsViewModel;
    }

    public void initialize() {
        shopsViewModel.getState().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
            if (newValue.getShops() != null) {
                listShops.getItems().clear();
                listShops.getItems().setAll(newValue.getShops());
            }
            if (newValue.getError() != null) {
                getPrincipalController().error(newValue.getError());
            }
            getPrincipalController().root.setCursor(Cursor.DEFAULT);
        }));
        listShops.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
            if (newValue != null) {
                textName.setText(newValue.getName());
            }
        }));
    }

    @Override
    public void principalCargado() {
        getPrincipalController().root.setCursor(Cursor.WAIT);
        shopsViewModel.initialize();
    }

    @FXML
    private void search() {
        String text = textSearch.getText();
        if (text != null && !text.isBlank()) {
            shopsViewModel.search(text);
        } else {
            getPrincipalController().error(Constants.WRITE_SOMETHING_TO_SEARCH);
        }
    }

    @FXML
    private void add() {
        String name = textName.getText();
        if (name != null && !name.isBlank()) {
            shopsViewModel.add(new Shop(name));
        } else {
            getPrincipalController().error(Constants.WRITE_A_NAME);
        }
    }

    @FXML
    private void update() {
        Shop shop = listShops.getSelectionModel().getSelectedItem();
        if (shop != null) {
            String name = textName.getText();
            if (name != null && !name.isBlank()) {
                if (!shop.getName().equalsIgnoreCase(name)) {
                    shop.setName(name);
                    shopsViewModel.update(shop);
                } else {
                    getPrincipalController().error(Constants.WRITE_A_DIFFERENT_NAME);
                }
            } else {
                getPrincipalController().error(Constants.WRITE_A_NAME);
            }
        } else {
            getPrincipalController().error(Constants.SELECT_A_SHOP_TO_UPDATE);
        }
    }

    @FXML
    private void delete() {
        Shop shop = listShops.getSelectionModel().getSelectedItem();
        if (shop != null) {
            shopsViewModel.delete(shop.getId());
        } else {
            getPrincipalController().error(Constants.SELECT_A_SHOP_TO_DELETE);
        }
    }

    @FXML
    private void crearFilters() {
        getPrincipalController().root.setCursor(Cursor.WAIT);
        shopsViewModel.getAllShops();
    }
}