package controller;

import java.io.File;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.User;
import view.RegisterMenu;


public class Orders {

    public static String trackNameToPlay="otherside.mp3";
    public static boolean GrayscaleEffect=false;
    public static boolean isMusicOn=true;
    public static MediaPlayer currentMediaPlayer;
    private static SecureRandom randomGenerator=new SecureRandom();


    public static void chooseRandomAvatarForUser(User user){
        int avatarNumber=randomGenerator.nextInt(3)+1;
        setUserAvatarByNumber(avatarNumber, user);
    }

    public static void setUserAvatarByNumber(int n, User user){
        switch (n) {
            case 1:
                user.setAvatarName("baldur.jpg");
                break;
            case 2:
                user.setAvatarName("lilwayne.jpg");
                break;

            default:
                user.setAvatarName("wolf.jpg");
                break;
        }
    }

    public static void playMedia(String songName){
        if(currentMediaPlayer != null)
            currentMediaPlayer.stop();
        if(!Orders.isMusicOn) return;
        Media media = null;
            try {
                media = new Media(new RegisterMenu().getClass().getResource("/music/"+songName).toURI().toString());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                currentMediaPlayer=mediaPlayer;
                mediaPlayer.play();
            }
        });
        
    }

    public static void playSoundEffect(){
        if(!isMusicOn) return;
        Media media = null;
            try {
                media = new Media(new RegisterMenu().getClass().getResource("/music/"+"swift-318.mp3").toURI().toString());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.play();
            }
        });
    }

    public static void changeMusicPlay(boolean value){
        isMusicOn=value;
        if(!isMusicOn&& currentMediaPlayer !=null) currentMediaPlayer.stop();
    }

    public static boolean isPasswordStrong(String password){

        if(password.length()<5) return false;

        Matcher numberMatcher=getMatcher("[0-9]", password);
        if(!numberMatcher.find()) return false;

        Matcher alphabetMatcher=getMatcher("[a-zA-Z]", password);
        if(!alphabetMatcher.find()) return false;

        return true;
    }

    public static void grayScale(Stage stage){
            ColorAdjust grayscale = new ColorAdjust();
            if(GrayscaleEffect)
                grayscale.setSaturation(-1);
            else 
                grayscale.setSaturation(0);
            stage.getScene().getRoot().setEffect(grayscale);
    }

    public static Matcher getMatcher(String regex, String input){
        Pattern pattern=Pattern.compile(regex);
        Matcher matcher=pattern.matcher(input);
        return matcher;
    }
}
