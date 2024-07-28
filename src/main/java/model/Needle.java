package model;

import javafx.util.Duration;
import view.ElementsRotationTransition;
import view.throwNeedleAnimation;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;

public class Needle extends Group {
    public Edge edge;
    public Ball ball;
    public Timeline disappearingTimeLine;
    private FadeTransition fadeTransition;

    private throwNeedleAnimation throwingAnimation;

    public Needle(){
        super();
        ball=new Ball(15);
        edge=new Edge(ball);
        this.getChildren().addAll(edge,ball.getBallGroup());
    }

    public void setThrowingAnimation(throwNeedleAnimation animation){
        this.throwingAnimation=animation;
    }

    public throwNeedleAnimation getThrowingAnimation(){
        return throwingAnimation;
    }

    public void throwNeedle(Pane pane,int windDegree){
        throwNeedleAnimation animation=new throwNeedleAnimation(pane, this,windDegree);
        this.setThrowingAnimation(animation);
        animation.play();
    }

    public double getNeedleTopY(){
        return ball.getCenterY()-edge.getHeight();
    }

    public static Needle createNewNeedle(){
        Needle newNeedle=new Needle();
        return newNeedle;
    }

    public void setDisappearingTimeline(){
        fadeTransition=new FadeTransition();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(50),
                actionEvent -> executeFadeTrans()));

        this.disappearingTimeLine=timeline;
        timeline.setCycleCount(-1);
        timeline.setDelay(Duration.millis(1000));
        timeline.setAutoReverse(true);
    }

    public void executeFadeTrans(){
        fadeTransition.setNode(this);
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();
    }
  

}
