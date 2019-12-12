package gui.home.controllers;

import gui.home.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import management.Account;
import management.AccountManager;
import management.GlobalSessionHolder;
import management.Session;
import management.account_types.Consumer;
import management.account_types.Producer;
import market.GlobalMarket;
import market.Reservation;

import javax.xml.bind.NotIdentifiableEvent;
import java.io.IOException;
import java.util.Optional;
import java.util.Stack;

public class HomeController {

    public Button monitor;
    public Button map;
    public Button reserve;
    public Button howTo;
    public Button friends;
    public Button buy;
    public Button sell;
    public Button crops;
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

    private void changeTitle(String name) {
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
    public void initialize() {
        previousMenus = new Stack<>();
        titles = new Stack<>();
        backButton.setDisable(true);
        accessController();
    }


    private void alertReservationsPending() {
        Producer userAccountProducerAdapter = (Producer) GlobalSessionHolder.currentSession.getSessionAccount();
        while (userAccountProducerAdapter.hasReservations()) {
            Reservation currentReservation = userAccountProducerAdapter.peekFrontReservation();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Reservation for: " + currentReservation.getBuyer().getName());
            alert.setHeaderText("Requested Crop: " + currentReservation.getProduct().getName());
            alert.setContentText("Name: " + currentReservation.getBuyer().getName() +
                    "\nAmount: " + currentReservation.getAmount());

            ButtonType buttonTypeOne = new ButtonType("Yes");
            ButtonType buttonTypeTwo = new ButtonType("No");

            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOne) {
                // ... user chose "One"
                userAccountProducerAdapter.addFrontReservationTo(GlobalMarket.getGlobalMarket());
            } else if (result.get() == buttonTypeTwo) {
                // ... user chose "Two"
                userAccountProducerAdapter.removeFrontReservation();


            }
        }
    }

        public void accessController(){
            if (GlobalSessionHolder.currentSession.getSessionAccount() instanceof Consumer) {
                //disables consumer-disabled features
                sell.setDisable(true);
                crops.setDisable(true);
            } else if (GlobalSessionHolder.currentSession.getSessionAccount() instanceof Producer) {
                //disables producer-disabled features
                buy.setDisable(true);
                friends.setDisable(true);
                alertReservationsPending();
            }
        }

        public void loadMap (MouseEvent mouseEvent){
        }
    }
