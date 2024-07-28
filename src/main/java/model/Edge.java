package model;

import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import view.ElementsRotationTransition;
import view.Game;

public class Edge extends Rectangle {



    public Edge (Ball ball){
        super(ball.getCenterX()-4/2, ball.getCenterY()-GameVariables.edgeHeight, 4, GameVariables.edgeHeight);
        this.setArcWidth(5);
        this.setArcHeight(5);
        this.setFill(javafx.scene.paint.Color.BLACK);
    }

    public Edge(double edgeX,double edgeY,double width,double height){
        super(edgeX, edgeY, width, height);
        this.setArcWidth(5);
        this.setArcHeight(5);
        this.setFill(javafx.scene.paint.Color.BLACK);
    }

    public double getDistanceFromCoord(double x, double y){
        return Math.sqrt((this.getX()-x)*(this.getX()-x)+(this.getY()-y)*(this.getY()-y));
    }
}
