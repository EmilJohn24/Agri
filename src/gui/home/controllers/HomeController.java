package gui.home.controllers;

import gui.home.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import javax.xml.bind.NotIdentifiableEvent;
import java.io.IOException;
import java.util.Stack;

public class HomeController  {

    @FXML
    private AnchorPane display;

    @FXML
    private Label title;

    @FXML
    private Button backButton;
    private Stack<Node> previousMenus;
    private Stack<String> titles;

    public void back() throws IOException {
        loadNodeIntoDisplay(previousMenus.pop());
        changeTitle(titles.pop());
        if (previousMenus.empty()) backButton.setDisable(true);
    }

    private void changeTitle(String name){
        titles.push(title.getText());
        title.setText(name);
    }

    public void buyMenu() throws IOException {
        load("buy.fxml", "Buy Crops");
    }

    public void howToUseApp() throws IOException {
        load("howtouse.fxml", "About Agri");
    }

    public void viewCrops() throws IOException {
        load("viewcrops.fxml", "Your Crops");
    }

    public void friendsMenu() throws IOException {
        load("viewfriends.fxml", "Your Friends");
    }

    public void sellMenu() throws IOException {
        load("sell.fxml", "Sell Crops");
    }

    public void reserveMenu() throws IOException {
        load("reserve.fxml", "Reserve Crops");
    }

    public void monitorMenu() throws IOException {
        load("monitor.fxml", "Watch Crops");
    }

    public void mapMenu() throws IOException {
        load("map.fxml", "Map ");
    }


    private void load(String filename, String title) throws IOException {
        loadDisplay(filename);
        changeTitle(title);
    }

    private void loadDisplay(String fileName) throws IOException {
        previousMenus.push(display.getChildren().get(0));
        backButton.setDisable(false);
        Parent node = FXMLLoader.load(Main.class.getResource("fxml/" + fileName));
        loadNodeIntoDisplay(node);
    }

    private void loadNodeIntoDisplay(Node node) {
        display.getChildren().removeAll(display.getChildren());
        display.getChildren().add(node);
    }

    @FXML
    public void initialize(){
        previousMenus = new Stack<>();
        titles = new Stack<>();
        backButton.setDisable(true);
    }

}
