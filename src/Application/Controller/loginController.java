package Application.Controller;

import Application.DAO.LoginDAO;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
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
import java.sql.SQLException;
import java.util.ResourceBundle;



public class loginController implements Initializable {
    @FXML
    TextField usernameTxt;
    @FXML
    PasswordField passwordTxt;
    @FXML
    AnchorPane loginPane;


    Stage stage;
    FXMLLoader loader = null;
    Parent root;


    String username;
    String password;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        usernameTxt.setOnAction(e -> passwordTxt.requestFocus());
        passwordTxt.setOnAction(e -> {
            try {
                login(e);
            } catch (InterruptedException | SQLException interruptedException) {
                interruptedException.printStackTrace();
            }
        });

        loadStage();

    }



    public void closeBtn () {
        Stage primaryStage = (Stage)  usernameTxt.getScene().getWindow();
       // ----Fading Code Experiment
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(400),loginPane);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.7);
        fadeTransition.play();
        fadeTransition.setOnFinished(event -> primaryStage.close() );

    }

    public void loadStage(){
        new Thread(() -> {
            try {
                loader = new FXMLLoader(getClass().getResource("/Application/View/mainMenu.fxml"));
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                stage = new Stage();
                stage.setMinWidth(1130);
                stage.setMinHeight(810);
                Scene scene = new Scene(root);
                scene.getStylesheets().add("/Application/CSS/MistSilver.css");
                stage.setScene(scene);
            });
    }).start();
    }

    public void login(ActionEvent event) throws InterruptedException, SQLException {

        username = usernameTxt.getText();
        password = passwordTxt.getText();


        if (username != null && password != null) {

            LoginDAO verifyUser = new LoginDAO();
            if (verifyUser.login(username, password) == true) {
                ////// Currently the Fading event is not working for Successful login
                closeBtn();
                stage.show();
            }
        }
    }



}
