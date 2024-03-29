package gui.home.controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import gui.home.Main;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import management.Account;
import management.AccountManager;
import management.GlobalSessionHolder;
import management.Session;
import management.account_types.Consumer;
import management.account_types.Producer;
import market.GlobalMarket;
import market.Reservation;
import objects.stack.Stack;

import javax.swing.*;
import javax.xml.bind.NotIdentifiableEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    public Button monitor;
    public Button map;
    public Button reserve;
    public Button howTo;
    public Button friends;
    public Button buy;
    public Button sell;
    public Button crops;
    public Button backButton;

    @FXML
    private AnchorPane display;
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private VBox box;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private Label title;

    @FXML
    private objects.stack.Stack<Node> previousMenus;
    private objects.stack.Stack<String> titles;

    public void back() throws IOException {
        loadNodeIntoDisplay(previousMenus.top());
        previousMenus.pop();
        changeTitle(titles.top());
        titles.pop();
        if (previousMenus.isEmpty()) backButton.setDisable(true);
    }

    private void pushAndChangeTitle(String name){
        titles.push(title.getText());
        changeTitle(name);
    }
    private void changeTitle(String name) {
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
        load("viewfriends.fxml", "Subscriptions");
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
        load("map.fxml", "Transactions");
    }

    public void logoutButton(ActionEvent event){


    }

    private void load(String filename, String title) throws IOException {
        loadDisplay(filename);
        pushAndChangeTitle(title);
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
                userAccountProducerAdapter.addFrontReservationTo(GlobalMarket.getGlobalMarket());
            } else if (result.get() == buttonTypeTwo) {
                userAccountProducerAdapter.removeFrontReservation();


            }
        }
    }

    private void alertNotifications(){
        Consumer consumer = (Consumer) GlobalSessionHolder.currentSession.getSessionAccount();
        while (consumer.hasNotifications()){
            Alert newAlert = new Alert(Alert.AlertType.INFORMATION);
            newAlert.setTitle("Notification");
            newAlert.setHeaderText(consumer.getNextNotification());
            newAlert.showAndWait();
        }
    }

    private void accessController(){
        if (GlobalSessionHolder.currentSession.getSessionAccount() instanceof Consumer) {
            //disables consumer-disabled features
            sell.setDisable(true);
            crops.setDisable(true);
            alertNotifications();
        } else if (GlobalSessionHolder.currentSession.getSessionAccount() instanceof Producer) {
            //disables producer-disabled features
            buy.setDisable(true);
            friends.setDisable(true);
            map.setDisable(true);
            reserve.setDisable(true);
            alertReservationsPending();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        previousMenus = new Stack<>();
        titles = new Stack<>();
        backButton.setDisable(true);
        accessController();
        HamburgerBackArrowBasicTransition burgerTask2 = new HamburgerBackArrowBasicTransition(hamburger);
        burgerTask2.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
            burgerTask2.setRate(burgerTask2.getRate() * -1);
            burgerTask2.play();
            if(drawer.isShown()) drawer.close();
            else drawer.open();
        });
        drawer.setSidePane(box);

    }

}
