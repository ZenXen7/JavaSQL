package JavaFXSQL;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertData extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Register Form");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(8);
        grid.setHgap(10);

        Label nameLabel = new Label("Username:");
        GridPane.setConstraints(nameLabel, 0, 0);

        TextField nameInput = new TextField();
        nameInput.setPromptText("Enter your username");
        GridPane.setConstraints(nameInput, 1, 0);

        Label passLabel = new Label("Password:");
        GridPane.setConstraints(passLabel, 0, 1);

        PasswordField passInput = new PasswordField();
        passInput.setPromptText("Enter your password");
        GridPane.setConstraints(passInput, 1, 1);

        Button registerButton = new Button("Register");
        GridPane.setConstraints(registerButton, 1, 2);
        registerButton.setOnAction((ActionEvent event) -> {
            try (Connection c = MySQLConnection.getConnection();
                 PreparedStatement statement = c.prepareStatement(
                         "INSERT INTO users (username, password) VALUES (?,?)"
                 )) {
                String username = nameInput.getText();
                String password = passInput.getText();
                statement.setString(1, username);
                statement.setString(2, password);
                int rows = statement.executeUpdate();
                System.out.println("Rows inserted: " + rows);
                System.out.println("Data Inserted Successfully");
                openLoginWindow();
                primaryStage.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        grid.getChildren().addAll(nameLabel, nameInput, passLabel, passInput, registerButton);

        Scene scene = new Scene(grid, 300, 150);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openLoginWindow() {
        Login login = new Login();
        login.start(new Stage());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
