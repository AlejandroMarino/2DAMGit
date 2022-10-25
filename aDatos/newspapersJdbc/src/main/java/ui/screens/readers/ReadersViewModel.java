package ui.screens.readers;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import servicios.ServiciosReader;
import ui.pantallas.filterArticles.FilterArticlesState;
import ui.pantallas.login.LoginState;

public class ReadersViewModel {

    private final ServiciosReader serviciosReader;

    private final ObjectProperty<ReadersState> state;

    public ReadOnlyObjectProperty<ReadersState> getState() {
        return state;
    }

    public ReadersViewModel(ServiciosReader serviciosReader) {
        state = new SimpleObjectProperty<>(new ReadersState(null, null));
        this.serviciosReader = serviciosReader;
    }


}
