package gui.home.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import management.AccountManager;
import management.account_types.Producer;
import market.GlobalMarket;
import market.Item;
import market.Product;

import java.util.ArrayList;
import java.util.LinkedList;

public class BuyController {
    @FXML
    private Label quantityLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private JFXComboBox<String> companyPicker;
    @FXML
    private GridPane cropGrid;
    private Product productPicked;
    private Producer companyPicked;
    private Item pendingSale;
    private ArrayList<Producer> producersWithProductPicked;
    private final int ROWS = 5;
    private final int COLS = 5;

    private void updateLabels(){
        quantityLabel.setText(String.valueOf(pendingSale.getQuantity()));
        priceLabel.setText(String.valueOf(pendingSale.getQuantity()));
    }
    @FXML
    public void initialize(){
        producersWithProductPicked = new ArrayList<>();
        companyPicker.setOnAction(event -> {
            companyPicked = producersWithProductPicked.get(companyPicker.getSelectionModel().getSelectedIndex());
            pendingSale = companyPicked.getItemInSale(productPicked);
            updateLabels();

        });
        initializeGrid();
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
}