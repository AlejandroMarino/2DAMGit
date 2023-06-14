package dao;

import domain.models.Arma;

import java.util.List;

public interface DaoArmas {

    void addArma(Arma arma);

    Arma getArma(int id);

    List<Arma> getAll();

    void update(Arma arma);

    void deleteArma(int id);

}
