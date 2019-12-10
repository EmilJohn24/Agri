package gui.home.controllers;

import gui.home.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import management.AccountManager;
import management.GlobalSessionHolder;

import java.io.IOException;

public class SignInController {
    @FXML
    public PasswordField passwordField;
    @FXML
    public TextField usernameField;


    public void submit(MouseEvent mouseEvent) throws IOException {
        GlobalSessionHolder.currentSession = AccountManager.getAccountManager().login(usernameField.getText(), passwordField.getText());
        if (GlobalSessionHolder.currentSession != null) {
            loadMain();
        }
        else{
            loginError();
        }
    }

    private void loginError(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid");
        alert.setHeaderText("Invalid username or password");
        alert.setContentText("Try again");

        alert.showAndWait();
    }

    private void loadMain() throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("fxml/Home.fxml"));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Agri");

        primaryStage.setScene(new Scene(root, 1920, 950));
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }
}
