package view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;

public class LoginMenuController {

    public PasswordField password;
    public TextField username;
    public  Text output_text;

    public void back() throws Exception{
        new RegisterMenu().start(RegisterMenu.stage);
    }
   
    public void login() throws Exception{
        setTextColor("yellow");
        User targetUser= User.getUserById(username.getText());
        if(targetUser==null) 
            output_text.setText("Invalid username!");

        else if(!targetUser.getPassword().equals(password.getText())) 
            output_text.setText("Incorrect password!");

        else {
            setTextColor("green");
            User.setCurrentUser(targetUser);
            new MainMenu().start(RegisterMenu.stage);
        }
    }

    public void setTextColor(String color){
        output_text.setStyle("-fx-fill:"+color);
    }

}
