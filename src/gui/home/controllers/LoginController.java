package gui.home.controllers;

import gui.home.Main;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public void open_register(ActionEvent actionEvent) {
        changeWhiteBox("register.fxml", pane.getLayoutX());
    }

    public void open_signin(ActionEvent actionEvent) {
        changeWhiteBox("signin.fxml", pane.getLayoutX() + pane.getPrefWidth() - vbox.getPrefWidth() - 30);
    }

    private void changeWhiteBox(String file, Double x) {
        TranslateTransition t = new TranslateTransition(Duration.seconds(1), vbox);
        t.setToX(x);
        t.play();
        t.setOnFinished((e)->{
            try {
                fxml = FXMLLoader.load(Main.class.getResource("fxml/" + file));
                vbox.getChildren().removeAll();
                vbox.getChildren().setAll(fxml);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    @FXML
    private VBox vbox;
    private Parent fxml;
    @FXML
    private Pane pane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      open_register(null);
    }
}
