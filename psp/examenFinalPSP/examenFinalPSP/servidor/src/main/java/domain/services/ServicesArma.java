package domain.services;

import domain.models.Arma;

import java.util.List;

public interface ServicesArma {

    Arma addArma(Arma arma);

    Arma update(Arma arma);

    List<Arma> getAll();

    void deleteArmaSinRelaciones(int id);

    void deleteArmaConRelaciones(int id);
}
