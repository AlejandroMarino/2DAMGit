package domain.services.servicesImpl;

import dao.DaoArmas;
import dao.DaoArmasPersonaje;
import dao.DaoSeries;
import domain.model.errores.BadRequestException;
import domain.model.errores.DataModificationException;
import domain.model.errores.NotFoundException;
import domain.models.Arma;
import domain.models.ArmaPersonaje;
import domain.models.Serie;
import domain.services.ServicesArma;
import jakarta.inject.Inject;

import java.util.List;

public class ServicesArmaImpl implements ServicesArma {

    private final DaoArmas dA;

    private final DaoArmasPersonaje dAP;

    private final DaoSeries dS;

    @Inject
    public ServicesArmaImpl(DaoArmas dA, DaoArmasPersonaje dAP, DaoSeries dS) {
        this.dA = dA;
        this.dAP = dAP;
        this.dS = dS;
    }


    @Override
    public Arma addArma(Arma arma) {
        Serie s = dS.getSerie(arma.getSerieId());
        if (s != null) {
            dA.getAll().forEach(arma1 -> {
                if (arma1.getId() == (arma.getId())) {
                    throw new BadRequestException("Ya existe un arma con ese nombre");
                }
            });
            dA.addArma(arma);
            return arma;
        } else {
            throw new NotFoundException("Serie no encontrada");
        }
    }

    @Override
    public Arma update(Arma arma) {
        Arma a = dA.getArma(arma.getId());
        if (a != null) {
            a.setDescripcion(arma.getDescripcion());
            dA.update(a);
            return arma;
        } else {
            throw new NotFoundException("Arma no encontrada");
        }
    }

    @Override
    public List<Arma> getAll() {
        return dA.getAll();
    }

    @Override
    public void deleteArmaSinRelaciones(int id) {
        Arma a = dA.getArma(id);
        if (a != null) {
            List<ArmaPersonaje> armaPersonajeList = dAP.getArmaPersonajeOfArma(id);
            if (armaPersonajeList.isEmpty()) {
                dA.deleteArma(id);
            } else {
                throw new DataModificationException("No se puede eliminar el arma porque tiene personajes asociados");
            }
        } else {
            throw new BadRequestException("Arma no encontrada");
        }
    }

    @Override
    public void deleteArmaConRelaciones(int id) {
        Arma a = dA.getArma(id);
        if (a != null) {
            dAP.deleteArmaPersonajeArma(id);
            dA.deleteArma(id);
        } else {
            throw new BadRequestException("Arma no encontrada");
        }
    }
}
