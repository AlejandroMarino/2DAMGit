package ui.pantallas.infoFish;

import common.Constantes;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;
import ui.common.BasePantallaController;

public class InfoFishController extends BasePantallaController {

    private final InfoFishViewModel infoFishViewModel;
    @FXML
    private ImageView iconFish;
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
                labelIsAllDay.setText(newValue.getFish().getAvailability().isAllDay() ? Constantes.YES : Constantes.NO);
                labelIsAllYear.setText(newValue.getFish().getAvailability().isAllYear() ? Constantes.YES : Constantes.NO);
                labelPrice.setText(String.valueOf(newValue.getFish().getPrice()));
                labelPriceCJ.setText(String.valueOf(newValue.getFish().getPrice_Cj()));
                labelCatchPhrase.setText(newValue.getFish().getCatch_Phrase());
                iconFish.setImage(new Image(infoFishViewModel.getFishLogo(newValue.getFish().getId())));
            }
        });
    }

    @Override
    public void principalCargado() {
        asynConSingle();
    }

    private void asynConSingle() {
        int id = getPrincipalController().getActualFish();
        Single.fromCallable(() -> infoFishViewModel.llamadaRetrofitAsyncEnUi(id))
                .subscribeOn(Schedulers.io())
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> getPrincipalController().root.setCursor(Cursor.DEFAULT))
                .subscribe(result ->
                                result.peek(infoFishViewModel::cambioState)
                                        .peekLeft(error -> getPrincipalController().error(error)),
                        throwable -> getPrincipalController().error(throwable.getMessage()));
        getPrincipalController().root.setCursor(Cursor.WAIT);
    }

    @FXML
    private void seeImage() {
        String name = infoFishViewModel.getFishName(getPrincipalController().getActualFish());
        getPrincipalController().goImageFish(name);
    }
}
