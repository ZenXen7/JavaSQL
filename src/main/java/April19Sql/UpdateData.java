package April19Sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateData {
    public static void main(String[] args) {
        try(Connection c = MySQLConnection.getConnection();
            PreparedStatement statement = c.prepareStatement("UPDATE users SET name=? , email=? WHERE id=?"
            )) {
            String name = "Bayot";
            String email = "bayot@gmail.com";
            int id = 1;
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setInt(3, id);

            int rows = statement.executeUpdate();

            System.out.println("Rows updated: " + rows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
