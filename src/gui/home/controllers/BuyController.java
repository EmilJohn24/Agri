package gui.home.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import market.GlobalMarket;
import market.Product;

import java.util.LinkedList;

public class BuyController {
    @FXML
    public GridPane cropGrid;
    private final int ROWS = 5;
    private final int COLS = 5;

    @FXML
    public void initialize(){
        initializeGrid();
    }

    private void pickCrop(String cropName){

    }

    private void initializeGrid(){
        LinkedList<Product> products = GlobalMarket.getGlobalMarket().getProductList();
        int index = 0;
        int size = products.size();
        for (Product p : products){
            JFXButton productButton = new JFXButton(p.getName());
            productButton.setOnMouseClicked(event -> pickCrop(p.getName()));
            cropGrid.getStyleClass().add("button");
            cropGrid.add(productButton, index % ROWS, index / COLS);
            index++;
        }

    }
}