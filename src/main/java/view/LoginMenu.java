package view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;

public class LoginMenu extends Application {
    public static Stage stage;

    public Label label;
    public TextField username;


    @Override
    public void start(Stage stage) throws Exception {
        LoginMenu.stage = stage;
        BorderPane borderPane = FXMLLoader.load(
            new URL(LoginMenu.class.getResource("/FXML/loginMenu.fxml").toExternalForm()));
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.show();
    }


}