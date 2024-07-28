package view;

import javafx.application.Application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;

import org.json.simple.parser.ParseException;

import controller.JsonConverter;
import controller.Orders;


public class ProfileMenu extends Application {
    public static Stage stage;

    public Text imageText,customImgText;
    public Image img1,img2,img3,customImg;
    public ImageView view1,view2,view3,customView;
    public Pane pane;
    public PasswordField password;
    public TextField username;
    public Button passwordChange,UsernameChange;
    public Text output_text;
    public VBox fieldsVbox;
    public Button back,deleteAccount;

    @Override
    public void start(Stage stage) throws Exception {
        ProfileMenu.stage=stage;
        Pane borderPane = FXMLLoader.load(
            new URL(RegisterMenu.class.getResource("/FXML/ProfileMenu.fxml").toExternalForm()));
        this.pane=borderPane;
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        initializeEsssentials();
        initializeUserImage();
        initializeChangingFields();
        initializeImageSelection();
        initializeButtons();
        Orders.grayScale(stage);
        stage.show();
    }

    private void initializeEsssentials(){
        output_text=new Text();
        output_text.setStyle(" -fx-font-size: 13px; -fx-text-fill: rgb(174, 193, 172);");
        fieldsVbox = new VBox(16);
        fieldsVbox.setStyle("");
        fieldsVbox.setLayoutX(180);
        fieldsVbox.setLayoutY(100);
        fieldsVbox.getChildren().add(output_text);
        pane.getChildren().add(fieldsVbox);
    }

    private void initializeUserImage() throws FileNotFoundException{
        if(User.getCurrentUser().getAvatarName()==null)
            Orders.chooseRandomAvatarForUser(User.getCurrentUser());
        customImgText=new Text("Your Current Avatar is:");
        customImg=new Image(new FileInputStream("src/main/resources/images/"+User.getCurrentUser().getAvatarName()));
        customView=new ImageView(customImg);
        customView.setFitWidth(50);
        customView.setFitHeight(50);
        HBox hBox= new HBox(8, customImgText,customView);
        fieldsVbox.getChildren().add(hBox);
    }

    private void initializeButtons(){
        back=new Button("Back");
        deleteAccount=new Button("Delete Account");
        deleteAccount.setStyle("-fx-color: rgba(201, 110, 24, 0.858); -fx-padding: 10;");
        HBox hBox = new HBox(16, back,deleteAccount);
        fieldsVbox.getChildren().addAll(hBox);

        back.setOnMouseClicked(event -> {
            try {
                back();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        deleteAccount.setOnMouseClicked(event -> {
            try {
                delete();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }

    private void back() throws Exception{
        new MainMenu().start(stage);
    }

    private void delete() throws Exception{
        JsonConverter.removeUserDataByUsername(User.getCurrentUser().getUsername(), "src/main/resources/data/users.json");
        User.getUsers().remove(User.getCurrentUser());
        User.setCurrentUser(null);
        new RegisterMenu().start(stage);
    }

    private void initializeImageSelection() throws URISyntaxException, FileNotFoundException{
        img1=new Image(new FileInputStream("src/main/resources/images/lilwayne.jpg"));
        img2=new Image(new FileInputStream("src/main/resources/images/baldur.jpg"));
        img3=new Image(new FileInputStream("src/main/resources/images/wolf.jpg"));
        view1=new ImageView(img1);
        view2=new ImageView(img2);
        view3=new ImageView(img3);
        view1.setFitHeight(50);
        view2.setFitHeight(50);
        view3.setFitHeight(50);
        view1.setFitWidth(50);
        view2.setFitWidth(50);
        view3.setFitWidth(50);
        imageText= new Text("Choose Your Avatar:");
        HBox hBox= new HBox(8, imageText, view1 , view2 , view3);
        fieldsVbox.getChildren().add(hBox);

        view1.setOnMouseClicked(event -> {
            try {
                setUserImage(1);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        view2.setOnMouseClicked(event -> {
            
            try {
                setUserImage(2);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        view3.setOnMouseClicked(event -> {
            try {
                setUserImage(3);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }

    private void setUserImage(int imgNum) throws FileNotFoundException{
       switch (imgNum) {
        case 1:
            User.getCurrentUser().setAvatarName("lilwayne.jpg");
            break;
        case 2:
            User.getCurrentUser().setAvatarName("baldur.jpg");
            break;
        default:
            User.getCurrentUser().setAvatarName("wolf.jpg");
            break;
       }
       loadUserAvatar();
       try {
        JsonConverter.putUserDataInFile(User.getCurrentUser(), User.getCurrentUser().getUsername(), "src/main/resources/data/users.json");
    } catch (ParseException e) {
        e.printStackTrace();
    }
    }

    private void loadUserAvatar() throws FileNotFoundException{
        customImg=new Image(new FileInputStream("src/main/resources/images/"+User.getCurrentUser().getAvatarName()));
        customView.setImage(customImg);

    }



    public void initializeChangingFields() throws ParseException{
        password=new PasswordField();
        username=new TextField();
        username.setStyle("       -fx-text-fill: #081162e8;    -fx-font-family: \"Arial\";-fx-font-size: 13px;");
        password.setStyle("  -fx-text-fill: #081162e8;    -fx-font-family: \"Arial\"; -fx-font-size: 13px;");
        passwordChange=new Button("Change Password");
        UsernameChange= new Button("Change Username");

        UsernameChange.setOnMouseClicked(event-> 
                {
                    try {
                        usernameChange();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                });

        passwordChange.setOnMouseClicked(event-> {
            try {
                passwordChange();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        HBox hbox1= new HBox(20, username,UsernameChange);
        HBox hbox2=new HBox(20, password,passwordChange);
        fieldsVbox.getChildren().addAll(hbox1,hbox2);

        username.setPromptText(User.getCurrentUser().getUsername());
        username.setOpacity(0.2);

        username.textProperty().addListener((observable, oldText, newText)->{
            username.setPromptText(newText);
            username.setOpacity(1);
        });

        password.textProperty().addListener((observable, oldText, newText)->{
            password.setOpacity(1);
            password.setPromptText(newText);
        });
    }

    public void usernameChange() throws ParseException{
        String newUsername=username.getText();
        if(User.getUserById(newUsername) != null)
            output_text.setText("Error: username already in use");
        else{
            JsonConverter.removeUserDataByUsername(User.getCurrentUser().getUsername(), "src/main/resources/data/users.json");
            User.getCurrentUser().setUsername(newUsername);
            JsonConverter.putUserDataInFile(User.getUserById(newUsername),newUsername,"src/main/resources/data/users.json");
            output_text.setText("Change was Succesful!");
        }
        username.setText("");
    }

    public void passwordChange() throws ParseException{
        String newPassword=password.getText();
        if(!Orders.isPasswordStrong(newPassword))
            output_text.setText("Error: password is weak!");
        else{
            JsonConverter.removeUserDataByUsername(User.getCurrentUser().getUsername(), "src/main/resources/data/users.json");
            User.getCurrentUser().setPassword(newPassword);
            JsonConverter.putUserDataInFile(User.getCurrentUser(),User.getCurrentUser().getUsername(),"src/main/resources/data/users.json");
            output_text.setText("Change was Succesful!");
        }
        password.setText("");
    }
}
