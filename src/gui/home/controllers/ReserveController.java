package gui.home.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import management.AccountManager;
import management.GlobalSessionHolder;
import management.Session;
import management.account_types.Consumer;
import management.account_types.Producer;
import market.GlobalMarket;
import market.Item;
import market.Product;
import market.Reservation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;

public class ReserveController {
    public JFXComboBox companyPicker;
    public TextField quantityBuyTextField;
    public JFXDatePicker datePicker;
    public Label reservationFeeLabel;
    public Label totalPriceLabel;
    public Button submit;
    public GridPane cropGrid;
    @FXML
    private Label priceLabel;
    @FXML
    private JFXComboBox selectedCompany;
    //TODO: Start reserve controller

    private Product productPicked;
    private Producer companyPicked;
    private Item pendingSale;
    private Integer pendingQuantity;
    private LocalDate pendingLocalDate;
    private ArrayList<Producer> producersWithProductPicked;

    private final int ROWS = 5;
    private final int COLS = 5;

    private void updateLabels(){
//        quantityLabel.setText(String.valueOf(pendingSale.getQuantity()));
        priceLabel.setText(String.valueOf(pendingSale.getPrice()));
        priceUpdate();
    }

    private Double getPendingTotalPrice(){
        return pendingSale.getPrice() * pendingQuantity;
    }
    private void priceUpdate(){
        totalPriceLabel.setText(String.valueOf(getPendingTotalPrice()));
    }


    @FXML
    public void initialize(){
        pendingLocalDate = LocalDate.now();
        datePicker.setValue(pendingLocalDate);
        pendingQuantity = 0;
        producersWithProductPicked = new ArrayList<>();
        initializeButtons();
        initializeGrid();
    }

    private void initializeButtons() {
        companyPicker.setOnAction(event -> {
            companyPicked = producersWithProductPicked.get(companyPicker.getSelectionModel().getSelectedIndex());
            pendingSale = companyPicked.getItemInStorage(productPicked);
            updateLabels();

        });
        quantityBuyTextField.setOnAction(event -> {
            //TODO: Check if there are enough stocks
            pendingQuantity =  Integer.valueOf(quantityBuyTextField.getText());
            priceUpdate();
        });

        datePicker.setOnAction(event -> {pendingLocalDate = datePicker.getValue();});

    }

    private void loadCompanies(){
        companyPicker.getItems().removeAll(companyPicker.getItems());
        producersWithProductPicked.addAll(AccountManager.getAccountManager().getProducersWithStored(productPicked));
        for (Producer p : producersWithProductPicked){
            companyPicker.getItems().add(p.getName());
        }
    }

    private void pickCrop(JFXButton buttonPicked, int index){
        //TODO: Fix coloring and selection problem. Company list not properly updated
        for (Node n : cropGrid.getChildren()) {
            if (n instanceof JFXComboBox)
                n.setStyle("-fx-background-color: WHITE;");
        }
        buttonPicked.setStyle("-fx-background-color: BLUE;");
        LinkedList<Product> products = GlobalMarket.getGlobalMarket().getProductList();
        productPicked = products.get(index);
        loadCompanies();
    }

    private void initializeGrid(){
        LinkedList<Product> products = GlobalMarket.getGlobalMarket().getProductList();
        int index = 0;
        int size = products.size();
        for (Product p : products){
            JFXButton productButton = new JFXButton(p.getName());
            int finalIndex = index;
            productButton.setOnMouseClicked(event -> pickCrop(productButton, finalIndex));
            cropGrid.getStyleClass().add("button");
            cropGrid.add(productButton, index % ROWS, index / COLS);
            index++;
        }
    }
    public void reserveAction(MouseEvent mouseEvent) {
        Reservation newReservation = new Reservation(companyPicked,
                GlobalSessionHolder.currentSession.getSessionAccount(), pendingLocalDate, productPicked, pendingQuantity);
        companyPicked.requestReservation(newReservation);
        Alert newAlert = new Alert(Alert.AlertType.INFORMATION);
        newAlert.setTitle("Reservation complete");
        newAlert.setContentText("Reservation is now pending.");
        newAlert.showAndWait();
    }
}
