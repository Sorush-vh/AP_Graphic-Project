package view;



import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import controller.Orders;
import controller.UserComparator;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.GameVariables;
import model.User;


public class SettingsMenu extends Application{

    Stage stage;
    Pane pane;
    VBox fieldsVbox;
    Label label;

    Text difficultyText;
    CheckBox easy,normal,hard;

    Text missionSelectionText;
    CheckBox m1,m2,m3;

    Text ballModificationText;
    CheckBox b1,b2,b3;

    Text grayScaleText;
    CheckBox grayScaleCheckbox;

    UserPreferences userPreferences;

    Button exit;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage=stage;
        Pane borderPane = FXMLLoader.load(
            new URL(RegisterMenu.class.getResource("/FXML/Settings.fxml").toExternalForm()));
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        this.pane=borderPane;
        setFieldsVbox();

        setLabel();
        initializeDifficultyFields();
        initializeMissionFields();
        initializeBallModifiers();
        initializeGrayscaleComponents();
        initializeUserPreferences();
        initializeExit();
        Orders.grayScale(stage);
        stage.show();
    }

    private void initializeExit(){
        exit=new Button("Back To Main Menu");
        exit.setStyle("-fx-color: rgba(201, 110, 24, 0.858);-fx-padding: 10;");
        exit.setOnMouseClicked(event -> {
            try {
                new MainMenu().start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        fieldsVbox.getChildren().add(exit);
    }

    private void initializeUserPreferences(){
         userPreferences=new UserPreferences(220, 300);
         fieldsVbox.getChildren().add(userPreferences);
    }

    private void setLabel(){
        label=new Label("SETTINGS:");
        label.setStyle("-fx-font-size: 24px; -fx-text-fill: #097575;");
        label.setLayoutX(220);
        label.setLayoutY(70);
        pane.getChildren().addAll(label);
    }


    private void setFieldsVbox(){
        fieldsVbox=new VBox(16);
        fieldsVbox.setLayoutX(220);
        fieldsVbox.setLayoutY(100);
        fieldsVbox.setStyle("-fx-text-fill: rgb(34, 46, 183);-fx-background-color: rgba(52, 35, 26, 0.363); -fx-border-radius: 24;");
        pane.getChildren().add(fieldsVbox);
    }

    private void initializeDifficultyFields(){
        difficultyText=new Text("Select Difficulty:(default: normal)");
        easy=new CheckBox(); normal=new CheckBox(); hard= new CheckBox();
        easy.selectedProperty().addListener(event -> setDifficulty(1,easy.isSelected()));
        normal.selectedProperty().addListener(event -> setDifficulty(2,normal.isSelected()));
        hard.selectedProperty().addListener(event -> setDifficulty(3,hard.isSelected()));

        HBox hbox=new HBox(20,difficultyText,easy,normal,hard);
        normal.setSelected(true);
        fieldsVbox.getChildren().add(hbox);
    }

    private void setDifficulty(int i, boolean isTrue){
        if(!isTrue) return;
        GameVariables.setDifficulty(i);
        switch (i) {
            case 1:
                normal.setSelected(false);
                hard.setSelected(false);
                break;
            case 2:
                easy.setSelected(false);
                hard.setSelected(false);
                break;
        
            default:
                easy.setSelected(false);
                normal.setSelected(false);
                break;
        }
    }

    private void initializeMissionFields(){
        missionSelectionText=new Text("Select Mission:(default: 1)");
        m1=new CheckBox(); m2=new CheckBox(); m3= new CheckBox();
        setLevel(GameVariables.missionNumber, true);
        m1.selectedProperty().addListener(event -> setLevel(1,m1.isSelected()));
        m2.selectedProperty().addListener(event -> setLevel(2,m2.isSelected()));
        m3.selectedProperty().addListener(event -> setLevel(3,m3.isSelected()));
        HBox hbox=new HBox(20,missionSelectionText,m1,m2,m3);
        fieldsVbox.getChildren().add(hbox);
    }

    private void setLevel(int missionNumber, boolean isTrue){
        if(!isTrue) return;
        GameVariables.setLevel(missionNumber);
        switch (missionNumber) {
            case 1:
                m1.setSelected(true);
                m2.setSelected(false);
                m3.setSelected(false);
                break;
            case 2:
                m2.setSelected(true);
                m1.setSelected(false);
                m3.setSelected(false);
                break;
        
            default:
                m3.setSelected(true);
                m1.setSelected(false);
                m2.setSelected(false);
                break;
        }
    }

    private void initializeBallModifiers(){
        ballModificationText=new Text("Modify number of balls in game: (-2,default,+2)");
        b1=new CheckBox(); b2=new CheckBox(); b3=new CheckBox();
        b1.selectedProperty().addListener(event -> setBalls(1,b1.isSelected()));
        b2.selectedProperty().addListener(event -> setBalls(2,b2.isSelected()));
        b3.selectedProperty().addListener(event -> setBalls(3,b3.isSelected()));

        b2.setSelected(true);
        HBox hBox=new HBox(16, ballModificationText,b1,b2,b3);
        fieldsVbox.getChildren().add(hBox);
    }

    private void setBalls(int val, boolean isTrue){
        if(!isTrue) return;
        switch (val) {
            case 1:
                b2.setSelected(false);
                b3.setSelected(false);
                GameVariables.modifyBalls(-2);
                break;
            case 2:
                b1.setSelected(false);
                b3.setSelected(false);
                GameVariables.modifyBalls(0);
                break;
            default:
                b2.setSelected(false);
                b1.setSelected(false);
                GameVariables.modifyBalls(2);
                break;
        }
    }

    private void initializeGrayscaleComponents(){
        grayScaleCheckbox=new CheckBox();
        grayScaleText=new Text("Change game colors to black and white: ");

        grayScaleCheckbox.setSelected(Orders.GrayscaleEffect);
        grayScaleCheckbox.selectedProperty().addListener(event -> handleColorEffect(grayScaleCheckbox.isSelected()));
        HBox hBox= new HBox(20, grayScaleText , grayScaleCheckbox);
        fieldsVbox.getChildren().add(hBox);
    }

    private void handleColorEffect(boolean value){
        Orders.GrayscaleEffect=value;
        Orders.grayScale(stage);
    }
}
