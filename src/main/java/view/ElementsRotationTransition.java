package view;



import javafx.scene.layout.Pane;

import java.security.SecureRandom;
import java.util.Random;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.transform.Rotate;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.Ball;
import model.GameElements;
import model.Needle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;

public class ElementsRotationTransition extends Rotate{
    
    public static GameElements gameElements;
    public static SecureRandom randomGenerator=new SecureRandom();

    private static double normalAngle=3;
    private static double freezeAngle=1;
    
    public boolean isOnFreeze=false;

    private boolean defaultDirection=true;
    private Pane pane;
    public Timeline timeline;
    public Timeline directionChangeTimeline;
    public double theta=0;

    public ElementsRotationTransition(Pane pane){
        super();
        this.setPivotX(gameElements.getMainCircle().getCenterX());
        this.setPivotY(gameElements.getMainCircle().getCenterY());
        this.setAngle(0);
        gameElements.getTransforms().add(this);
        
        timeline=createTimeline();
    }

    public Timeline createTimeline(){

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(40),
                actionEvent -> executeRotate()));

        this.timeline=timeline;
        timeline.setCycleCount(-1);
        timeline.setDelay(Duration.millis(0));
        return timeline;
    }

    private void executeRotate(){

        int angleModifier=1;
        if(!defaultDirection) angleModifier=-1;

        if(!isOnFreeze) 
            this.setAngle( (this.getAngle()+angleModifier*normalAngle)%360 );
        else    
            this.setAngle( (this.getAngle()+angleModifier*freezeAngle)%360 );

        theta=this.getAngle();
    }

    public void applyTickle(double angle){
        Rotate rotate=CreateRotate(angle, gameElements.getMainCircle().getCenterX(), gameElements.getMainCircle().getCenterY());
        gameElements.getTransforms().add(rotate);
    }

    public Rotate CreateRotate(double angle, double xpivot, double ypivot){
        Rotate rotate=new Rotate();
        rotate.setPivotX(gameElements.getMainCircle().getCenterX());
        rotate.setPivotY(gameElements.getMainCircle().getCenterY());
        rotate.setAngle(-gameElements.rotateTransition.getAngle());
        return rotate;
    }

    public void setDirectionChangeTimeline(int duration){
        Timeline timelinez = new Timeline(new KeyFrame(Duration.millis(duration*1000),
                actionEvent -> defaultDirection=!defaultDirection));

        this.directionChangeTimeline=timelinez;
        timelinez.setDelay(Duration.millis(0));
    }

    public void initializeDirectionChange(){
        int duration=4+randomGenerator.nextInt(4);
        setDirectionChangeTimeline(duration);
        directionChangeTimeline.play();
        directionChangeTimeline.setOnFinished(event -> directionChangeEvent() );
    }

    private void directionChangeEvent(){
        initializeDirectionChange();
    }

    public void setClone(Ball ball){
        Circle cloneBall= ball.cloneBall();
        gameElements.stickedNeedlesBall.add(cloneBall);
        cloneBall.getTransforms().add(this);
    }
}
