package view;

import javafx.application.Application;

import java.io.File;

import org.json.simple.parser.ParseException;

import controller.Orders;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Ball;
import model.EndgameNotification;
import model.GameElements;
import model.GameVariables;
import model.Needle;
import model.User;
import model.WindMeasureComponents;

public class Game extends Application {

    public static boolean isGameGoingOn=true;
    public static int nextCheckpoint=2;
    public static int scoreValue;
    public static Game currentGame;


    public Pane gamePane;
    public Stage stage;
    public GameElements gameElements;
    public Needle preparedNeedle;
    ProgressBar freezeBar;
    Text freezePercentage;
    Text scoreText;
    HBox freezeHBox;
    Text OutputText;
    WindMeasureComponents windComponents;
    int timeLeft;
    Text timeText;
    Timeline timeTimeline;
 

    @Override
    public void start(Stage stage) throws Exception {
        Pane gamePane = FXMLLoader.load(Game.class.getResource("/fxml/Game.fxml"));
        this.gamePane=gamePane;
        currentGame=this;
        this.stage=stage;

        setNewNeedlesStartingPos(200, 560);
        initializeInGameElements();
        gameElements.initializeRotation();

        Scene scene = new Scene(gamePane);
        stage.setScene(scene);
        
        SetUserProgressData();
        initializeTexts();
        GameVariables.runMission( currentGame);
        setTimer();
        Orders.grayScale(stage);
        stage.show();
    }



    private void initializeInGameElements(){
        Circle mainCircle= new Circle(200, 200, 100, javafx.scene.paint.Color.BLACK);
        gameElements= new GameElements(mainCircle, gamePane);
        throwNeedleAnimation.gameElements=gameElements;
        gamePane.getChildren().add( gameElements);
        ElementsRotationTransition.gameElements=gameElements;

        windComponents=new WindMeasureComponents(50, Ball.XofBalls, Ball.YofBalls-80,gamePane);
    }

    private void setNewNeedlesStartingPos(int ballX, int ballY){
        Ball.XofBalls=ballX;
        Ball.YofBalls=ballY;
    }

    private void setTimer(){
        timeLeft=GameVariables.missionTime;
        timeText=new Text(450, 30, "time left:"+timeLeft/60+":"+timeLeft%60);
        timeText.setStyle("-fx-text-fill: rgb(22, 32, 9);-fx-background-color: rgba(72, 70, 76, 0.306)");
        freezeHBox.getChildren().add(timeText);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000),
                actionEvent -> decreaseTime()));

        this.timeTimeline=timeline;
        timeline.setCycleCount(-1);
        timeline.setOnFinished(event -> handleEndOfGame());
        timeline.setDelay(Duration.millis(0));
        timeline.play();
    }

    private void decreaseTime(){
        timeLeft--;
        timeText.setText("time left:"+timeLeft/60+":"+timeLeft%60);
    }


    public void SetUserProgressData(){
        freezeHBox=new HBox(20);
        freezeHBox.setAlignment(Pos.CENTER_RIGHT);
        freezeHBox.setLayoutX(350);
        freezeHBox.setStyle("-fx-background-color: rgba(72, 70, 76, 0.306)");
        freezeBar=new ProgressBar(1);
        freezeBar.setProgress(0.2);
        freezeHBox.getChildren().add( freezeBar);

        freezePercentage= new Text(0, 0, "Freeze Progress: 0%");
        freezePercentage.setStyle("-fx-text-fill: rgb(22, 32, 9);");

        scoreText=new Text(0,0,"Your Score: "+scoreValue);
        scoreText.setStyle("-fx-text-fill: rgb(22, 32, 9);");

        freezeHBox.getChildren().addAll(freezePercentage,scoreText);

        gamePane.getChildren().addAll(freezeHBox);
        
    }

    public void addToFreezeProgress(double chargeValue){
        if(freezeBar.getProgress()+chargeValue<1)
            freezeBar.setProgress(freezeBar.getProgress()+chargeValue);

        else { 
            freezeBar.setProgress(1);
            OutputText.setText("Freeze loaded!: press "+GameVariables.keyBinds.get("freeze")+" to freeze!");
        }
    }


    public void createNeedle(Pane gamePane) {
        Needle needle = Needle.createNewNeedle();
        gamePane.getChildren().addAll(needle);
        gamePane.getChildren().get(gamePane.getChildren().size()-1).requestFocus();
        preparedNeedle=needle;

        needle.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String keyName = keyEvent.getCode().getName();

                if (keyName.equals(GameVariables.keyBinds.get("shoot")))
                    handleNeedleThrow(needle);
                
                else if (keyName.equals(GameVariables.keyBinds.get("freeze")))
                    freezeElements();

                else if (keyEvent.getCode()==KeyCode.ESCAPE)
                    pauseGame(); 

                else if (keyName.equals(GameVariables.keyBinds.get("left")))
                    moveLeft(needle);

                else if (keyName.equals(GameVariables.keyBinds.get("right")))
                    moveRight(needle);;
            }
        });
    }


    public void pauseGame(){
        gameElements.rotateTransition.timeline.pause();
        timeTimeline.pause();
        PauseMenu pauseMenu= new PauseMenu(gamePane, stage,this);
        pauseMenu.requestFocus();
        gamePane.getChildren().add(pauseMenu);
    }

    public void handleNeedleThrow(Needle needle){
        needle.throwNeedle(gamePane,windComponents.getWindAngle());
        if(GameVariables.IsGameLaunched){
        addToFreezeProgress(0.1);
        handleFreezePercentage();
        handleScoreChange();
        if(GameVariables.numberOfBallsInGame<=0 && GameVariables.IsGameLaunched){
            isGameGoingOn=false;
            handleEndOfGame();
        }
            
        }
        createNeedle(gamePane);
    }

    private void handlePhaseChange(){

        int passedCheckpoint=nextCheckpoint-1;
        switch (passedCheckpoint) {
            case 1:
                startDirectionChange();
                resizeBalls();
                break;
            case 2:
                freezeHBox.setStyle("-fx-background-color: rgb(130, 170, 8);");
                fadeBalls();
                break;
            case 3:
                freezeHBox.setStyle("-fx-background-color: rgb(6, 163, 27);");
                startWindEffect();
                break;
        }
        OutputText.setText("Entered Phase "+nextCheckpoint);
        nextCheckpoint++;
        Orders.playSoundEffect();
    }

    private void initializeTexts(){
        HBox hBox=new HBox();
        hBox.setStyle("-fx-background-color: rgba(72, 70, 76, 0.306)");

        OutputText=new Text();
        OutputText.setText("Phase 1: enjoy:)");
        OutputText.setStyle("-fx-text-fill: rgb(22, 32, 9);");

        hBox.getChildren().add(OutputText);
        hBox.setLayoutX(400);
        hBox.setLayoutY(450);

        gamePane.getChildren().add(hBox);
    }

    private void handleScoreChange(){
        if(isGameGoingOn)
        scoreValue++;
        scoreText.setText("Your Score: "+scoreValue);

        if(scoreValue> ((nextCheckpoint-1)*GameVariables.totalBallNumber)/4)
            handlePhaseChange();
    }

    private void handleFreezePercentage(){
        double progressInPercent=freezeBar.getProgress()*100;
        freezePercentage.setText("Percentage: "+(int)progressInPercent+"%");
    }

    private void freezeElements(){
        if(freezeBar.getProgress()<1) {
            OutputText.setText("You cant use Freeze right now!");
            return;
        }
        gameElements.rotateTransition.isOnFreeze=true;

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(GameVariables.freezeTimer*1000),
            actionEvent ->  gameElements.rotateTransition.isOnFreeze=false));

        timeline.play();
        freezeBar.setProgress(0);
        OutputText.setText("");
        handleFreezePercentage();
    }

    private void resizeBalls(){
        for ( Needle needle : gameElements.stickedNeedles ) 
            needle.ball.startBallResizing();
    }

    private void fadeBalls(){
        for ( Needle needle : gameElements.stickedNeedles ) {
                needle.setDisappearingTimeline();
                needle.disappearingTimeLine.play();
        }
    }

    private void startDirectionChange(){
        gameElements.rotateTransition.initializeDirectionChange();
    }

    private void startWindEffect(){
        windComponents.startGeneratingDegree();
    }

    private void moveRight(Needle needle){
        if(nextCheckpoint-1<=3) return;
        if(needle.edge.getX()>Ball.XofBalls+24) return;
        needle.ball.setCenterX(needle.ball.getCenterX()+4);
        needle.edge.setX(needle.edge.getX()+4);
        needle.ball.text.setX(needle.ball.text.getX()+4);
    }

    private void moveLeft(Needle needle){
        if(nextCheckpoint-1<=3) return;
        if(needle.edge.getX()<Ball.XofBalls-24) return;
        needle.ball.setCenterX(needle.ball.getCenterX()-4);
        needle.edge.setX(needle.edge.getX()-4);
        needle.ball.text.setX(needle.ball.text.getX()-4);
    }

    public void handleEndOfGame(){
        int totalScore=0;
        if(!isGameGoingOn && GameVariables.IsGameLaunched){
            gamePane.setStyle(" -fx-background-color: rgba(208, 166, 216, 0.39);");
            totalScore=(int) getTotalScore();
            int timeTaken=GameVariables.missionTime-timeLeft;
            try {
                if(User.getCurrentUser()!=null)
                User.getCurrentUser().storeMissionScore(totalScore, GameVariables.missionNumber, GameVariables.missionDifficulty,timeTaken);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        EndgameNotification notif=new EndgameNotification(stage, currentGame, totalScore, GameVariables.numberOfBallsInGame<=0);
        gamePane.getChildren().add(notif);
        GameVariables.resetGameVariables();
        if(timeTimeline != null)
            timeTimeline.stop();
        timeTimeline=null;
    }


    private double getTotalScore(){
        if(GameVariables.numberOfBallsInGame<=0)
            return   ( scoreValue*(1+0.25*( (double)timeLeft/GameVariables.missionTime )+(double) GameVariables.missionDifficulty/3));
        else
            return ( scoreValue*(1+ (double) GameVariables.missionDifficulty/3));
            
    }

}
