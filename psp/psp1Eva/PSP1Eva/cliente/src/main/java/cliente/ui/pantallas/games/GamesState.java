package cliente.ui.pantallas.games;

import domain.models.Game;
import domain.models.Shop;
import lombok.Data;

import java.util.List;

@Data
public class GamesState {
    private final List<Game> games;
    private final List<Shop> shops;
    private final Shop shop;
    private final String error;
}
