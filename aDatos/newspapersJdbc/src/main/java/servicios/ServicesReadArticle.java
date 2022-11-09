package servicios;

import data.DaoReadArticle;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.ReadArticle;

public class ServicesReadArticle {

    private DaoReadArticle dRA;

    @Inject
    public ServicesReadArticle(DaoReadArticle dRA) {
        this.dRA = dRA;
    }

    public String readArticle(ReadArticle ra) {

        //Falta comprobar que exista el reader y el article y este suscrito al newspaper
        int result = dRA.add(ra);
        if (result == -1) {
            return "connection error";
        } else if (result == -2) {
            return "error adding read article";
        } else if (result == -3) {
            return "error in data of the database";
        } else {
            return "";
        }
    }
}
