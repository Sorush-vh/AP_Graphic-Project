package view;

import java.io.FileInputStream;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;

public class RegisterMenu extends Application {
    public static Stage stage;
    public static Text output;

    public static void main(String[] args) {
        launch(args);
        //TODO: SET ALL STAGES
    }

    @Override
    public void start(Stage stage) throws Exception {
        RegisterMenu.stage = stage;
        RegisterMenuController.stage=stage;
        BorderPane borderPane = FXMLLoader.load(
            new URL(RegisterMenu.class.getResource("/FXML/RegisterMenu.fxml").toExternalForm()));
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.getIcons().add(new Image(new FileInputStream("src/main/resources/images/edge.png")));
        stage.show();
    }

}
