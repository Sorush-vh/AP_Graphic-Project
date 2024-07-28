package model;

import java.util.HashMap;

import controller.Orders;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import view.Game;

public class GameVariables {
    public static boolean IsGameLaunched=false;
    public static HashMap<String, String> keyBinds;
    public static int numberOfBallsInGame;
    public static int totalBallNumber;
    public static int missionNumber=1;
    public static int missionDifficulty=2;
    public static double edgeHeight;
    public static int missionTime;
    public static double freezeTimer;
    public static int needleSpeed;
    private static int ballModifier;

    static{
        setDefaultBinds();
    }

    private static void setDefaultBinds(){
        keyBinds=new HashMap<String, String>();
        keyBinds.put("shoot", KeyCode.SPACE.getName());
        keyBinds.put("freeze", "F");
        keyBinds.put("right", "W");
        keyBinds.put("left", "Q");
    }

    private static void setMission1(Game game){
        game.createNeedle(game.gamePane);
            Timeline timelinez = new Timeline(new KeyFrame(Duration.millis(250),
                actionEvent -> setDefaultNeedles(game)));
            timelinez.setDelay(Duration.millis(0));
            timelinez.setCycleCount(3);
            timelinez.play();
            timelinez.setOnFinished(event -> timeLineEndEvent(game));
    }
    
    private static void timeLineEndEvent(Game game){
        IsGameLaunched=true;
        game.gamePane.getChildren().remove(game.preparedNeedle);
        setNeedlesCount();
        game.createNeedle(game.gamePane);
        Orders.playMedia(Orders.trackNameToPlay);
    }

    private static void setMission2(Game game){
        game.createNeedle(game.gamePane);
        Timeline timelinez = new Timeline(new KeyFrame(Duration.millis(400),
                actionEvent -> setDefaultNeedles(game)));
            timelinez.setDelay(Duration.millis(0));
            timelinez.setCycleCount(4);
            timelinez.play();
            timelinez.setOnFinished(event -> timeLineEndEvent(game));
    }

    private static void setMission3(Game game){
        game.createNeedle(game.gamePane);
        Timeline timelinez = new Timeline(new KeyFrame(Duration.millis(500),
                actionEvent -> setDefaultNeedles(game)));
            timelinez.setDelay(Duration.millis(0));
            timelinez.setCycleCount(5);
            timelinez.play();
            timelinez.setOnFinished(event -> timeLineEndEvent(game));
    }

    public static void runMission( Game game){
        edgeHeight=70;
        setMissionsTime();
        applyDifficulty();
        switch (missionNumber) {
            case 1:
                setMission1(game);
                break;
            case 2:
                setMission2(game);
                break;
            case 3:
                setMission3(game);
                break;
            default:
                break;
        }
    }

    public static void resetGameVariables(){
        numberOfBallsInGame=0;
        IsGameLaunched=false;
    }

    private static void setDefaultNeedles(Game game){
        game.handleNeedleThrow(game.preparedNeedle);
    }

    public static void setDifficulty(int level){
        missionDifficulty=level;
    }

    public static void setLevel(int levelNumber){
        missionNumber=levelNumber;
    }

    public static void modifyBalls(int number){
       ballModifier=number;
    }

    private static void setNeedlesCount(){
        switch (missionNumber) {
            case 1:
                numberOfBallsInGame=10;
                break;
            case 2:
                numberOfBallsInGame=13;
                break;
            case 3:
                numberOfBallsInGame=16;
                break;
            default:
                break;
        }
        totalBallNumber=numberOfBallsInGame+ballModifier;
        numberOfBallsInGame+=ballModifier;
    }

    private static void applyDifficulty(){
        switch (missionDifficulty) {
            case 1:
                freezeTimer=7;
                needleSpeed=13;
                edgeHeight+=20;
            case 2:
                freezeTimer=5;
                needleSpeed=10;
                edgeHeight+=10;
                missionTime-=15;
                break;
            case 3:
                freezeTimer=3;
                needleSpeed=7;
                missionTime-=30;
                break;
            default:
                break;
        }
    }

    private static void setMissionsTime(){
        switch (missionNumber) {
            case 1:
                missionTime=60;
                break;
            case 2:
                missionTime=75;
                break;
            case 3:
                missionTime=90;
                break;
            default:
                break;
        }
    }

    


}
