package gui.home.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import management.AccountManager;
import management.GlobalSessionHolder;
import management.account_types.Consumer;
import management.account_types.Producer;
import market.GlobalMarket;
import market.Item;
import market.Product;
import objects.list.List;

import java.util.ArrayList;
import java.util.LinkedList;

public class BuyController {
    @FXML
    private Button submit;
    @FXML
    private TextField quantityBuyTextField;
    @FXML
    private Label totalPriceLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private JFXComboBox<String> companyPicker;
    @FXML
    private GridPane cropGrid;

    private Product productPicked;
    private Producer companyPicked;
    private Item pendingSale;
    private Integer pendingQuantity;
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

    public void buyOperation(){
        Consumer customer = (Consumer) GlobalSessionHolder.currentSession.getSessionAccount();
        customer.addItemBought(pendingSale.subtractQuantity(pendingQuantity));
        Alert success = new Alert(Alert.AlertType.INFORMATION);
        success.setTitle("Buy");
        success.setHeaderText("Successful transaction!");
        success.setContentText("Producer: " + pendingSale.getSeller().getName() + "\nProduct: " + pendingSale.getProduct().getName() +
                "\nTotal price: Php" + getPendingTotalPrice() +
                "\nQuantity: " + pendingQuantity);
        success.showAndWait();
    }
    @FXML
    public void initialize(){
        pendingQuantity = 0;
        producersWithProductPicked = new ArrayList<>();
        initializeButtons();
        initializeGrid();
    }

    private void initializeButtons() {
        companyPicker.setOnAction(event -> {
            companyPicked = producersWithProductPicked.get(companyPicker.getSelectionModel().getSelectedIndex());
            pendingSale = companyPicked.getItemInSale(productPicked);
            updateLabels();

        });
        quantityBuyTextField.setOnAction(event -> {
            //TODO: Check if there are enough stocks
            pendingQuantity =  Integer.valueOf(quantityBuyTextField.getText());
            priceUpdate();
        });

        submit.setOnMouseClicked(event -> buyOperation());
    }

    private void loadCompanies(){
        companyPicker.getItems().removeAll(companyPicker.getItems());
        producersWithProductPicked.addAll(AccountManager.getAccountManager().getProducersWith(productPicked));
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
        List<Product> products = GlobalMarket.getGlobalMarket().getProductList();
        productPicked = products.get(index);
        loadCompanies();
    }

    private void initializeGrid(){
        List<Product> products = GlobalMarket.getGlobalMarket().getProductList();
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
}