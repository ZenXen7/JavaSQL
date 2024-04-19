package JavaFXSQL;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UserList extends Stage {

    public UserList() {
        setTitle("User List");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        ListView<String> userList = new ListView<>();
        ObservableList<String> items = FXCollections.observableArrayList();

        try (Connection c = MySQLConnection.getConnection();
             PreparedStatement statement = c.prepareStatement("SELECT username FROM users");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                items.add(username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        userList.setItems(items);
        Button updateButton = new Button ("Update Selected User");
        updateButton.setOnAction(actionEvent -> {
            String selected = userList.getSelectionModel().getSelectedItem();
            if(selected != null){
                try(Connection c = MySQLConnection.getConnection();
                    PreparedStatement statement = c.prepareStatement("UPDATE users SET username=? WHERE username=?")) {
                    Scanner sc = new Scanner(System.in);
                    System.out.print("Update username into: ");
                    String newUsername = sc.nextLine();
                    statement.setString(1, newUsername);
                    statement.setString(2, selected);

                    int rows = statement.executeUpdate();
                    System.out.println("Rows updated: " + rows);

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });




        Button deleteButton = new Button("Delete Selected User");
        deleteButton.setOnAction(event -> {
            String selectedUser = userList.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                try (Connection c = MySQLConnection.getConnection();
                     PreparedStatement statement = c.prepareStatement("DELETE FROM users WHERE username = ?")) {
                    statement.setString(1, selectedUser);
                    int rows = statement.executeUpdate();
                    if (rows > 0) {
                        items.remove(selectedUser);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        layout.getChildren().addAll(userList, updateButton, deleteButton);

        Scene scene = new Scene(layout, 300, 200);
        setScene(scene);
    }
}
