package ui.pantallas.imagenFish;

import lombok.Data;
import modelo.Fish;

@Data
public class ImageFishState {
    private final Fish fish;
    private final String error;
}
