package dao;

import model.Raton;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class RatonesDao {
    private static List<Raton> ratones = new ArrayList<>();

    static {
        ratones.add(new Raton(1, "uno", 1));
        ratones.add(new Raton(2, "dos", 1));
        ratones.add(new Raton(3, "tres", 1));
        ratones.add(new Raton(4, "cuatro", 1));
        ratones.add(new Raton(5, "cinco", 1));
    }

    public List<Raton> getRatones(){
        return ratones;
    }

    public Raton addRaton(Raton raton){
        ratones.add(raton);
        return raton;
    }
}
