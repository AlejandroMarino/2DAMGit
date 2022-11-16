package servicios;

import data.*;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.Article;
import modelo.Newspaper;
import modelo.ReadArticle;

import java.util.List;

public class ServicesReadArticle {

    private DaoReadArticle dRA;
    private DaoReader dR;
    private DaoSubscribe dS;
    private DaoArticle dA;
    private DaoNewspaper dN;

    @Inject
    public ServicesReadArticle(DaoReadArticle dRA, DaoReader dR, DaoSubscribe dS, DaoNewspaper dN, DaoArticle dA) {
        this.dRA = dRA;
        this.dR = dR;
        this.dS = dS;
        this.dN = dN;
        this.dA = dA;
    }

    public Either<String, List<ReadArticle>> getAll() {
        if (dRA.getAll().isLeft()) {
            return Either.left("Error getting read articles");
        } else {
            return Either.right(dRA.getAll().get());
        }
    }

    public boolean delete(ReadArticle ra) {
        if (getAll().isLeft()) {
            return false;
        } else {
            return dRA.delete(ra.getIdArticle(), ra.getIdReader()) > 0;
        }
    }

    public boolean add(ReadArticle ra) {

        if (dR.getReader(ra.getIdReader()).isLeft()) {
            return false;
        } else {
            Article art = dA.get(ra.getIdArticle()).get();
            Newspaper newp = dN.get(art.getIdNewspaper()).get();
            if (dS.get(ra.getIdReader(), newp.getId()).isLeft()) {
                return false;
            } else {
                return dRA.add(ra) > 0;
            }
        }
    }
}
