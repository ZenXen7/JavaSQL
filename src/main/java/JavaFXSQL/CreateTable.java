package JavaFXSQL;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {
    public static void main(String[] args) {
        try(Connection c = MySQLConnection.getConnection();
            Statement statement = c.createStatement())
        {
            String query = "CREATE TABLE IF NOT EXISTS users(" + "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "username VARCHAR(50) NOT NULL," + "password VARCHAR(100) NOT NULL)";
            statement.execute(query);
            System.out.println("Table created succesfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
