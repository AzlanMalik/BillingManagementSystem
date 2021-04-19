package Application.Controller;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class loginController implements Initializable {
    @FXML
    TextField usernameTxt;
    @FXML
    PasswordField passwordTxt;
    @FXML
    AnchorPane loginPane;


    Stage stage;
    Parent root;



    String username;
    String password;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //
        usernameTxt.setOnAction(e -> passwordTxt.requestFocus());
        passwordTxt.setOnAction(e -> {
            try {
                login(e);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        });

    }



    public void closeBtn () throws InterruptedException {
        Stage primaryStage = (Stage)  usernameTxt.getScene().getWindow();
       // ----Fading Code Experiment
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000),loginPane);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.3);
        fadeTransition.play();
        fadeTransition.setOnFinished(event -> primaryStage.close() );

    }

    public void login(ActionEvent event) throws InterruptedException {
        if(usernameTxt.getText().equals("admin") && passwordTxt.getText().equals("admin")){
                username = usernameTxt.getText();
                password = passwordTxt.getText();
                closeBtn();
            try {

                root = FXMLLoader.load(getClass().getResource("/Application/FXML/mainMenu.fxml"));
            stage = new Stage();
            stage.setMinWidth(1130);
            stage.setMinHeight(810);
            stage.setScene(new Scene(root));
            stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }



        }

    }
}
