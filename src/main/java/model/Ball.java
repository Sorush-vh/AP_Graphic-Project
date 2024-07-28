package model;

import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.util.Duration;
import model.Needle;
import view.ElementsRotationTransition;
import view.Game;
import view.throwNeedleAnimation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;

public class Ball extends Circle {

    public static double XofBalls;
    public static double YofBalls;

    private boolean positiveResize=true;
    private double defaultRadius;
    public Text text;
    public Group group;
    public Timeline ballResizeTimeline;
    public Circle cloneBall;


    public Ball(double radius){
        super(XofBalls,YofBalls, radius);
        this.setFill(javafx.scene.paint.Color.WHITE);
        this.setStroke(javafx.scene.paint.Color.BLACK);
        this.setStrokeWidth(2);
        this.defaultRadius=radius;
        createTextOfBall();

        group= new Group();
        group.getChildren().addAll(this,text);
        if(GameVariables.IsGameLaunched)
        GameVariables.numberOfBallsInGame--;
    }

    private void createTextOfBall(){
        
        int xModifier=getNumberXmodifier();
        Text text = new Text(XofBalls+xModifier, YofBalls+4,"");
        text.setBoundsType(TextBoundsType.VISUAL);
        text.setStyle("-fx-text-fill: black; -fx-font-size: 16px; -fx-text-alignment: center; -fx-alignment: center;");
        text.setText(""+GameVariables.numberOfBallsInGame);
        this.text=text;
    }

    private int getNumberXmodifier(){
        int xModifier;
        if(GameVariables.numberOfBallsInGame>=10) xModifier=-8;
        else xModifier=-5;
        return xModifier;
    }

    public Group getBallGroup(){
        return group;
    }

    public void createResizeTimeline(){
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100),
                actionEvent -> executeResize()));

        this.ballResizeTimeline=timeline;
        timeline.setCycleCount(-1);
        timeline.setDelay(Duration.millis(0));
    }

    private void executeResize(){
        if(this.getRadius()>defaultRadius*1.15){
            positiveResize=false;
            // if(!areBallsDisjoint()) Game.currentGame.isGameGoingOn=false;
            // Game.currentGame.handleEndOfGame();
        }
        if(this.getRadius()<defaultRadius)
            positiveResize=true;
        
        if(positiveResize){
            this.setRadius(this.getRadius()+1);
            this.cloneBall.setRadius(this.getRadius()+1);
        }
        else {
            this.setRadius(this.getRadius()-1);
            this.cloneBall.setRadius(this.getRadius()-1);
        }
    }

    public void startBallResizing(){
        createResizeTimeline();
        this.ballResizeTimeline.play();
    }

    public Circle cloneBall(){
        Circle circle=new Circle(this.getCenterX(),this.getCenterY(), this.getRadius());
        circle.setOpacity(0);
        throwNeedleAnimation.gameElements.pane.getChildren().add(circle);
        cloneBall=circle;
        return circle;
    }

    public static boolean areBallsDisjoint(){
        ArrayList<Needle> stickedNeedles=ElementsRotationTransition.gameElements.stickedNeedles;
        Needle targetNeedle;
        for (int i = 0; i <stickedNeedles.size() ; i++) {
            targetNeedle=stickedNeedles.get(i);
            for (int j = 0; j < stickedNeedles.size(); j++) {
                if(j==i) continue;
                
                if(targetNeedle.ball.cloneBall.getBoundsInParent().intersects(stickedNeedles.get(j).ball.cloneBall.getBoundsInParent()))
                    return false;
            }
        }
        return true;
    }
}
