package view;

import java.net.URL;

import controller.Orders;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;

public class RegisterMenuController {


    public static Stage stage;
    public  Text output_text;
    public Label label;
    public PasswordField password;
    public TextField username;

    public Button guest_button;
    public Button register_button;
    public Button exit_Button;
    public Button login_button;





    public void exit(){
        stage.close();
    }

    public void register() throws Exception{
        setTextColor("yellow");
        String userId=username.getText();
        String userPass=password.getText();
        if(User.getUserById(userId) != null)
            output_text.setText("Username already in use!");
        else{
            if(!Orders.isPasswordStrong(userPass))
                output_text.setText("Please choose a stronger password: minimum 5 characters, containing letter and number.");
            else{
                User.createUser(userId, userPass);
                setTextColor("green");
                output_text.setText("Register succesful!");
                new LoginMenu().start(stage);
            }
        }
    }

    public void login() throws Exception{
        new LoginMenu().start(stage);
    }

    public void runAsGuest() throws Exception{
        LoginMenu.stage=stage;
       new MainMenu().start(stage);
    }

    public void setTextColor(String color){
        output_text.setStyle("-fx-fill:"+color);
    }
}
