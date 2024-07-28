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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;



public class ScoreboardMenu extends Application{
    

    HBox sortingHbox;
    Text sortingText;
    CheckBox s0,s1,s2,s3;
    VBox rankingVbox;
    Label label;
    Pane pane;
    Stage stage;
    Button back,startNextmission;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage=stage;
        Pane borderPane = FXMLLoader.load(
            new URL(RegisterMenu.class.getResource("/FXML/ScoreboardMenu.fxml").toExternalForm()));
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);

        this.pane=borderPane;
        setLabel();
        initializeSortingChoices();
        initializeButtons();
        Orders.grayScale(stage);
        stage.show();
    }

    private void setLabel(){
        label=new Label("SCOREBOARD:");
        label.setStyle("-fx-font-size: 24px; -fx-text-fill: #097575;");
        label.setLayoutX(200);
        label.setLayoutY(135);
        pane.getChildren().addAll(label);
    }

    private void initializeSortingChoices(){
        sortingHbox=new HBox(20);
        sortingHbox.setLayoutX(200);
        sortingHbox.setLayoutY(172);
        sortingText= new Text("Choose Ranking Method (overall, easy, normal or hard difficulty)");
        s0=new CheckBox();
        s1= new CheckBox();
        s2= new CheckBox();
        s3 = new CheckBox();
        s0.selectedProperty().addListener(event -> changeSort(0,s0.isSelected()));
        s1.selectedProperty().addListener(event -> changeSort(1,s1.isSelected()));
        s2.selectedProperty().addListener(event -> changeSort(2,s2.isSelected()));
        s3.selectedProperty().addListener(event -> changeSort(3,s3.isSelected()));
        s0.setSelected(true);
        sortingHbox.getChildren().addAll(sortingText,s0,s1,s2,s3);
        pane.getChildren().add(sortingHbox);
    }

    private void changeSort(int type, boolean value){
        if(!value) return;
        UserComparator.comparingMode=type;
        if(rankingVbox!=null){
            rankingVbox.getChildren().clear();
            rankUsers();
        }
        else initializerankVbox();
        
        switch (type) {
            case 0:
                s1.setSelected(false);
                s2.setSelected(false);
                s3.setSelected(false);
                break;
            case 1:
                s0.setSelected(false);
                s2.setSelected(false);
                s3.setSelected(false);
                break;
            case 2:
                s0.setSelected(false);
                s1.setSelected(false);
                s3.setSelected(false);
                break;
        
            default:
                s0.setSelected(false);
                s1.setSelected(false);
                s2.setSelected(false);
                break;
        }
    }

    private void initializerankVbox(){
        rankingVbox = new VBox(0);
        rankingVbox.setLayoutX(200);
        rankingVbox.setLayoutY(200);
        pane.getChildren().add(rankingVbox);
        rankUsers();
    }

    private void rankUsers(){
        Collections.sort(User.getUsers(), new UserComparator());
        for (int i = 0; i < Math.min(10, User.getUsers().size()); i++) {
            User targetUser=User.getUsers().get(i);
            HBox userInfo=new HBox(25);
            Text userRank=new Text("Rank: "+(i+1));
            Text userName=new Text("Username: "+targetUser.getUsername());
            Text userScore=new Text();
            Text userTime=new Text("Time: "+targetUser.getUserOverallTime());
            fillUserFields(userScore, targetUser);
            setUserFieldStyle(i, userInfo);
            userInfo.getChildren().addAll(userRank,userName,userScore,userTime);
            rankingVbox.getChildren().add(userInfo);
        }
    }

    private void setUserFieldStyle(int i,HBox userInfo ){
            if(i==0) userInfo.setStyle("-fx-background-color: rgba(216, 216, 5, 0.646);");
            else if(i==1) userInfo.setStyle("-fx-background-color: rgba(121, 137, 139, 0.646);");
            else if(i==2) userInfo.setStyle("-fx-background-color: rgba(67, 50, 12, 0.646);");
            else userInfo.setStyle("-fx-background-color: rgba(74, 44, 68, 0.646);");
            //gold:rgba(216, 216, 5, 0.646);
            //silver:rgba(121, 137, 139, 0.646);
            //bronze:rgba(67, 50, 12, 0.646);
            //default:rgba(74, 44, 68, 0.646);
    }

    private void fillUserFields(Text userScore, User user){
        switch (UserComparator.comparingMode) {
            case 0:
                userScore.setText("Score:"+user.getUserOverallScore());
                break;
            case 1:
                userScore.setText("Score:"+user.getDifficultyScores()[0]);
                break;
            case 2:
                userScore.setText("Score:"+user.getDifficultyScores()[1]);
                break;
            default:
                userScore.setText("Score:"+user.getDifficultyScores()[2]);
                break;
        }
    }

    private void initializeButtons(){
        back=new Button("Back");
        back.setStyle(" -fx-color: rgba(201, 110, 24, 0.858); -fx-padding: 10;-fx-text-fill: rgb(34, 46, 183);");
        back.setOnMouseClicked(event -> {
            try {
                back();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        startNextmission=new Button("Start Next Game!");
        startNextmission.setStyle(" -fx-border: 2px , solid , green; -fx-color: rgb(121, 180, 72); -fx-padding: 10;-fx-text-fill: rgb(34, 46, 183);");
        startNextmission.setOnMouseClicked(event -> {
            try {
                startNextGame();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        HBox hBox= new HBox(20);
        hBox.getChildren().addAll(startNextmission,back);
        hBox.setStyle("    -fx-start-margin: 20;");
        pane.getChildren().add(hBox);
        hBox.setLayoutX(250);
        hBox.setLayoutY(380);
    }

    private void back() throws Exception{
        new MainMenu().start(stage);
    }

    private void startNextGame() throws Exception{
        new Game().start(stage);
    }


}
