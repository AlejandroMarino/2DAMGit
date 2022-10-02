package ui.pantallas.allFish;

import lombok.Data;
import modelo.Fish;

import java.util.List;

@Data
public class AllFishState {
    private final List<Fish> fishes;
    private final String error;
}
