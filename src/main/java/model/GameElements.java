package model;

import javafx.util.Duration;
import view.ElementsRotationTransition;

import java.util.ArrayList;

import javafx.animation.RotateTransition;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;

public class GameElements extends Group {
    
    public Pane pane;
    public ElementsRotationTransition rotateTransition;
    public ArrayList <Needle> stickedNeedles=new ArrayList<Needle>();
    public ArrayList <Circle> stickedNeedlesBall=new ArrayList<Circle>();

    public GameElements(Circle mainCircle,Pane pane){
        super();
        this.getChildren().add(mainCircle);
        this.pane=pane;
    }

    public Circle getMainCircle(){
        return (Circle) this.getChildren().get(0);
    }

    public void initializeRotation(){
       ElementsRotationTransition rotateTrans=new ElementsRotationTransition(pane);
       this.rotateTransition=rotateTrans;
       rotateTrans.timeline.play();
    }

    public double getBottomOfBallY(){
        return getMainCircle().getCenterY()+getMainCircle().getRadius();
    }

    public double getAngleDegreeOfNeedle(Needle needle){
        double deltaX=needle.ball.getCenterX()-getMainCircle().getCenterX();
        double deltaY=needle.ball.getCenterY()-getMainCircle().getCenterY();
        return Math.atan(deltaX/deltaY)*180/Math.PI;
    }

}
