package dao;

import domain.models.Serie;

import java.util.List;

public interface DaoSeries {

    List<Serie> getAll();

    Serie getSerie(int id);
}
