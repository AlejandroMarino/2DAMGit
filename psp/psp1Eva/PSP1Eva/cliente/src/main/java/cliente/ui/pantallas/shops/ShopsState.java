package cliente.ui.pantallas.shops;

import domain.models.Shop;
import lombok.Data;

import java.util.List;

@Data
public class ShopsState {
    private final List<Shop> shops;
    private final String error;
}
