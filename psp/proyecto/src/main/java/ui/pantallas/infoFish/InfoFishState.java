package ui.pantallas.infoFish;

import lombok.Data;
import modelo.Fish;

@Data
public class InfoFishState {
    private final Fish fish;
    private final String error;
}
