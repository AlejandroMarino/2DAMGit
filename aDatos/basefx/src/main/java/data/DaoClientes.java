package data;

public class DaoClientes {
    private DataBase db;
    public DaoClientes(DataBase db) {
        this.db = db;
    }
    public DaoClientes() {
        this.db = new DataBase();
    }


}
