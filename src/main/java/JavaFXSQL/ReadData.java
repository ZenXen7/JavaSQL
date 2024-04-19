package JavaFXSQL;



import April19Sql.MySQLConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReadData {

    public static void main(String[] args) {
        try(Connection c = MySQLConnection.getConnection();
            Statement statement = c.createStatement()) {
            String query = "SELECT * FROM users";
            ResultSet res = statement.executeQuery(query);
            while(res.next()){
                int id = res.getInt("id");
                String username = res.getString("username");
                String password = res.getString("password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
