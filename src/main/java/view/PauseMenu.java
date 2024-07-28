package view;

import controller.Orders;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PauseMenu extends VBox{
    
    UserPreferences userPreferences;
    Text musicSelected;
    CheckBox m1,m2,m3;
    Button resume,exit;
    Game menuGame;

    public PauseMenu(Pane gamePane, Stage stage, Game game){
        super();
        this.setLayoutX(300);
        this.setLayoutY(130);
        menuGame=game;
        resume = new Button("Resume Game");
        exit = new Button("Exit Game");
        exit.setStyle("-fx-color: rgba(201, 110, 24, 0.858);-fx-padding: 10;");

        resume.setOnMouseClicked(event -> resumeGame(gamePane));
        exit.setOnMouseClicked(event -> {
            try {
                new MainMenu().start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        initializeMusicChoices();
        userPreferences=new UserPreferences(0, 0);
        userPreferences.setStyle(" -fx-color:  rgba(120, 18, 106, 0.368);-fx-spacing: 16;");
        this.getChildren().addAll(resume,exit,userPreferences);
        this.setStyle("-fx-background-color: rgba(52, 35, 26, 0.363);");
    }

    private void initializeMusicChoices(){
        musicSelected=new Text("Select Game Music Track:");
        m1=new CheckBox("passionFruit");
        m2=new CheckBox("otherside");
        m3= new CheckBox("colorViolet");
        m2.setSelected(true);
        m1.selectedProperty().addListener(event -> changeMusic(m1,1));
        m2.selectedProperty().addListener(event -> changeMusic(m2,2));
        m3.selectedProperty().addListener(event -> changeMusic(m3,3));
        HBox hbox= new HBox(20, musicSelected,m1,m2,m3);
        this.getChildren().add(hbox);
    }

    private void changeMusic(CheckBox musicBox,int number){
        if(!musicBox.isSelected()){
            return;
        }
        if(Orders.currentMediaPlayer != null){
            Orders.currentMediaPlayer.stop();
            Orders.currentMediaPlayer=null;
        }
        Orders.trackNameToPlay=musicBox.getText()+".mp3";
        switch (number) {
            case 1:
                m2.setSelected(false);
                m3.setSelected(false);
                break;
            case 2:
                m1.setSelected(false);
                m3.setSelected(false);
            break;
            default:
                m1.setSelected(false);
                m2.setSelected(false);
                break;
        }
    }

    private void playGameMusic(){
        if(Orders.currentMediaPlayer == null)
            Orders.playMedia(Orders.trackNameToPlay);
    }

    private void resumeGame(Pane gamePane){
        gamePane.getChildren().remove(this);
        menuGame.timeTimeline.play();
        menuGame.gameElements.rotateTransition.timeline.play();
        menuGame.preparedNeedle.requestFocus();
        playGameMusic();
    }
}
