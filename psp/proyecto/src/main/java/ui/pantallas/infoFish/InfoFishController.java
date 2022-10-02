package ui.pantallas.infoFish;

import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ui.common.BasePantallaController;

public class InfoFishController extends BasePantallaController {

    private final InfoFishViewModel infoFishViewModel;
    @FXML
    private ImageView imgFish;
    @FXML
    private Label labelName;
    @FXML
    private Label labelRarity;
    @FXML
    private Label labelLocation;
    @FXML
    private Label labelIsAllDay;
    @FXML
    private Label labelIsAllYear;
    @FXML
    private Label labelPrice;
    @FXML
    private Label labelPriceCJ;
    @FXML
    private Label labelCatchPhrase;

    @Inject
    public InfoFishController(InfoFishViewModel infoFishViewModel) {
        this.infoFishViewModel = infoFishViewModel;
    }

    public void initialize() {
        infoFishViewModel.getState().addListener((observable, oldValue, newValue) -> {
            if (newValue.getError() != null) {
                getPrincipalController().error(newValue.getError());
            }
            if (newValue.getFish() != null) {
                labelName.setText(newValue.getFish().getName().getName_EUen());
                labelRarity.setText(newValue.getFish().getAvailability().getRarity());
                labelLocation.setText(newValue.getFish().getAvailability().getLocation());
                labelIsAllDay.setText(newValue.getFish().getAvailability().isAllDay() ? "Yes" : "No");
                labelIsAllYear.setText(newValue.getFish().getAvailability().isAllYear() ? "Yes" : "No");
                labelPrice.setText(String.valueOf(newValue.getFish().getPrice()));
                labelPriceCJ.setText(String.valueOf(newValue.getFish().getPrice_Cj()));
                labelCatchPhrase.setText(newValue.getFish().getCatch_Phrase());
                imgFish.setImage(new Image(infoFishViewModel.getFishImage(newValue.getFish().getId())));
            }
        });
    }

    @Override
    public void principalCargado() {
        infoFishViewModel.inicio(getPrincipalController().getActualFish());
    }
}
