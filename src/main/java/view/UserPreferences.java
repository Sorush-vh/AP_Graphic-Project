package view;

import controller.Orders;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.GameVariables;

public class UserPreferences extends VBox {

    public CheckBox musicBox;
    public Text musicText;

    public TextField needleShootVal,moveLeft,moveRight,freeze;
    public Button submitChanges;

    public UserPreferences( int x, int y){
        super();
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.setSpacing(16);
        initializeMusicComponents();
        setKeyBindChanges();
    }

    private void initializeMusicComponents(){
        musicText=new Text("Game Audios: ");
        musicText.setStyle("-fx-color: rgb(187, 220, 56)");
        musicBox=new CheckBox();
        musicBox.selectedProperty().addListener(event -> changeMusicAvailability(musicBox.isSelected()));
        HBox hBox=new HBox(20, musicText,musicBox);
        this.getChildren().addAll(hBox);
        musicBox.setSelected(Orders.isMusicOn);
    }

    private void changeMusicAvailability(boolean val){
        Orders.changeMusicPlay(val);
    }

    private void setKeyBindChanges(){

        needleShootVal=new TextField();
        freeze=new TextField();
        moveLeft=new TextField();
        moveRight=new TextField();


        submitChanges=new Button("Submit New Keybinds");
        submitChanges.setStyle("-fx-padding: 10;-fx-color: rgb(187, 220, 56);-fx-text-fill: rgb(34, 46, 183);");
        submitChanges.setOnMouseClicked(event -> changeAllBinds());

        setFieldsPrompText();

        this.getChildren().addAll(needleShootVal,freeze,moveRight,moveLeft,submitChanges);
    }

    private void setFieldsPrompText(){
        needleShootVal.setPromptText("shooting: "+GameVariables.keyBinds.get("shoot"));
        freeze.setPromptText("freeze: "+GameVariables.keyBinds.get("freeze"));
        moveLeft.setPromptText("move left: "+GameVariables.keyBinds.get("left"));
        moveRight.setPromptText("move right: "+GameVariables.keyBinds.get("right"));

        needleShootVal.setText("");
        freeze.setText("");
        moveLeft.setText("");
        moveRight.setText("");
    }

    private void changeFieldBind(String fieldName, TextField field){
        if(field.getText().length() != 1) return;
        String newKey=field.getText().toUpperCase();
        GameVariables.keyBinds.put(fieldName, newKey);
    }

    private void changeAllBinds(){
        changeFieldBind("shoot", needleShootVal);
        changeFieldBind("freeze", freeze);
        changeFieldBind("left", moveLeft);
        changeFieldBind("right", moveRight);
        setFieldsPrompText();
    }
}
