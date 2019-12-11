package gui.home.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import management.GlobalSessionHolder;
import management.account_types.Producer;
import market.Item;

import java.util.ArrayList;

public class SellController {
    @FXML
    private Button addToMarketButton;
    @FXML
    private Button removeFromMarketButton;
    @FXML
    private Button updateCurrentProductButton;
    @FXML
    private ListView<String> cropsInStockView;
    @FXML
    private ListView<String> cropsForSale;
    @FXML
    private Label priceLabel;
    @FXML
    private Label quantityLabel;

    private Producer currentAccount;

    private void updateLabels(){
        int stockUpdateIndex = cropsInStockView.getSelectionModel().getSelectedIndices().get(0);
        int saleUpdateIndex = cropsForSale.getSelectionModel().getSelectedIndices().get(0);
        String quantityString, priceString;
        if (stockUpdateIndex >= 0) {
            priceString = String.valueOf(currentAccount.getProductsStored().get(stockUpdateIndex).getPrice());
            quantityString = String.valueOf(currentAccount.getProductsStored().get(stockUpdateIndex).getQuantity());
        }
        else if (saleUpdateIndex >= 0){
            priceString = String.valueOf(currentAccount.getProductsForSale().get(saleUpdateIndex).getPrice());
            quantityString = String.valueOf(currentAccount.getProductsForSale().get(saleUpdateIndex).getQuantity());
        }else{

            priceString = "0.00";
            quantityString = "0";
        }
        priceLabel.setText(priceString);
        quantityLabel.setText(quantityString);
    }
    private void updateCrops(){
        loadCropsInStock();
        loadCropsForSale();
        updateLabels();
    }
    private void loadCropsInStock(){
        cropsInStockView.getItems().clear();
        ArrayList<Item> accountItems = currentAccount.getProductsStored();
        for (Item i : accountItems){
            cropsInStockView.getItems().add(i.getProduct().getName());
        }
    }

    private void loadCropsForSale(){
        cropsForSale.getItems().clear();
        ArrayList<Item> saleItems = currentAccount.getProductsForSale();
        for (Item i : saleItems){
            cropsForSale.getItems().add(i.getProduct().getName());
        }
    }

    private void addToMarket(){
        for (Integer index : cropsInStockView.getSelectionModel().getSelectedIndices()) {
            currentAccount.addProductForSale(currentAccount.transferItem(index, currentAccount.getProductsStored().get(index).getQuantity()));
        }
        updateCrops();
    }

    private void removeFromMarket(){
        for (Integer index : cropsForSale.getSelectionModel().getSelectedIndices()){
            currentAccount.addProductInStorage(currentAccount.transferItemFromSales(index, currentAccount.getProductsForSale().get(index).getQuantity()));
        }
        updateCrops();
    }

    private void updateCurrentProduct(){
        //TODO: Ano ginagawa nito?
    }



    private void buttonSetup(){
        addToMarketButton.setOnMouseClicked(event -> addToMarket());
        removeFromMarketButton.setOnMouseClicked(event -> removeFromMarket());
        updateCurrentProductButton.setOnMouseClicked(event -> updateCurrentProduct());
        cropsForSale.setOnMouseClicked(event -> updateLabels());
        cropsInStockView.setOnMouseClicked(event -> updateLabels());
    }


    @FXML
    public void initialize(){
        currentAccount = (Producer) GlobalSessionHolder.currentSession.getSessionAccount();
        buttonSetup();
        updateCrops();
    }

































}
