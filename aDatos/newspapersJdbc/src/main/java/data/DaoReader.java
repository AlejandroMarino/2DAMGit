package data;

import config.DBConnection;
import jakarta.inject.Inject;

import java.sql.*;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoReader {

    private DBConnection db;

    @Inject
    public DaoReader(DBConnection db) {
        this.db = db;
    }

    private void readRS(ResultSet rs) {
        try {
            while (rs.next()) {
                int readerId = rs.getInt("id");
                String readerName = rs.getString("name_reader");
                LocalDate date = rs.getDate("birth_date").toLocalDate();
                System.out.println(readerId + ", " + readerName + ", " + date);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getAll(){
        try (Connection con = db.getConnection();
             Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {

            ResultSet rs = statement.executeQuery("SELECT * FROM reader");
            readRS(rs);

        } catch (SQLException ex) {
            Logger.getLogger(DaoReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int delete(int id) {
        int rowsAffected=0;
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM reader WHERE id=id")) {
            preparedStatement.setInt(1, id);
            rowsAffected= preparedStatement.executeUpdate();

        } catch (SQLException sqle) {
            Logger.getLogger(DaoReader.class.getName()).log(Level.SEVERE, null, sqle);
        }
        return rowsAffected;
    }

    public int saveWithAutoIncrementalID (String name, LocalDate birth_d){
        int rowsAffected=0;
        try (Connection con = db.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO reader (name_reader, birth_date) VALUES (name,birth_d)",
                     Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(2, name);
            preparedStatement.setDate(3, birth_d);

            rowsAffected= preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next()) {
                int auto_id = rs.getInt(1);
                System.out.println("The id of the new row is "+auto_id);
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }
}
