package model;

import java.security.SecureRandom;
import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import model.Needle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;

public class WindMeasureComponents {

    public int windDegree;
    public Circle angleIllustrator;
    public Rectangle circleConvertor;
    public Rectangle angleMeter;
    public Text angleText;

    private Rotate meterRotate;
    private SecureRandom randomGenerator=new SecureRandom();

    public WindMeasureComponents(double radius, double centerX, double  centerY ,Pane gamePane ){
        angleIllustrator=new Circle(centerX, centerY, radius);
        circleConvertor=new Rectangle(centerX-radius, centerY, 2*radius, radius);
        angleMeter=new Rectangle(centerX-4/2, centerY-radius*0.9, 4,radius*0.9);
        angleMeter.setFill(Color.rgb(181, 206, 69, 0.651));
        angleMeter.setArcWidth(4);
        angleMeter.setArcHeight(4);
        angleText=new Text(centerX-16, centerY-radius-15, "Wind: 0'");
        angleText.setFill(Color.rgb(181, 206, 69, 0.651));

        gamePane.getChildren().add(angleIllustrator);
        gamePane.getChildren().add(circleConvertor);
        gamePane.getChildren().add(angleMeter);
        gamePane.getChildren().add(angleText);

        setMeterRotate();
        createTextMeasuresOfWind(radius, centerX, centerY,gamePane);
        setStyleToComponents();
    }

    private void createTextMeasuresOfWind(double radius, double centerX, double  centerY,Pane gamePane){
        Text zeroText= new Text(centerX-3, centerY-radius,"0'");
        Text maxText=new Text(centerX+radius, centerY, "90'");
        Text minText = new Text(centerX-radius-18, centerY, "-90'");
        gamePane.getChildren().addAll( minText,maxText,zeroText);
    }

    private void setStyleToComponents(){
        Color c = Color.rgb(98, 140, 161, 1);  
        circleConvertor.setFill(c);
        angleIllustrator.setFill(Color.rgb(64, 83, 110));
    }

    public void startGeneratingDegree(){
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(5000),
                actionEvent -> setNewRandomDegree()));
        timeline.setDelay(Duration.millis(0));
        timeline.setCycleCount(1);
        timeline.play();
        timeline.setOnFinished(event -> handleRecurrence());
    }

    private void setNewRandomDegree(){
        windDegree = randomGenerator.nextInt(25)-12;
        meterRotate.setAngle(windDegree);
        angleText.setText("Wind: "+windDegree+"'");
    }

    private void setMeterRotate(){
        meterRotate=new Rotate();
        meterRotate.setPivotX(angleIllustrator.getCenterX());
        meterRotate.setPivotY(angleIllustrator.getCenterY());
        angleMeter.getTransforms().add(meterRotate);
    }

    private void handleRecurrence(){
        startGeneratingDegree();
    }

    public int getWindAngle(){
        return windDegree;
    }

}
