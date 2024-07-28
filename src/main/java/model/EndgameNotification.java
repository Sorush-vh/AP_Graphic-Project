package model;

import controller.Orders;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.Game;
import view.MainMenu;

public class EndgameNotification extends VBox{
    public Button proceed;
    public Text scoreText;
    public Label endGamelabel;

    public EndgameNotification(Stage stage, Game game,int finalScore,boolean isWon){
        super(20);
        this.setLayoutX(300);
        this.setLayoutY(200);
        proceed=new Button("Proceed");
        proceed.setOnMouseClicked(event -> {
            try {
                if(Orders.currentMediaPlayer!=null)
                    Orders.currentMediaPlayer.stop();
                new MainMenu().start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        scoreText=new Text("Your Score was: "+finalScore);
        endGamelabel=new Label();
        setEndingStyle(isWon);
        this.getChildren().addAll(endGamelabel,scoreText,proceed);
    }

    private void setEndingStyle(boolean isWon){
        if(isWon){
            endGamelabel.setText("Game Finished: Won!");
            this.setStyle("-fx-background-color: rgba(22, 113, 17, 0.354);");
        }
        else{
            endGamelabel.setText("Game Finished: Lost!");
            this.setStyle("-fx-background-color: rgba(113, 36, 17, 0.354);");
        }
    }
}
