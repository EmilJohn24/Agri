package gui.home.controllers;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import management.GlobalSessionHolder;
import management.account_types.Producer;
import market.GlobalMarket;
import market.Item;
import market.Product;
import objects.list.List;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SellController {
    @FXML
    public Label recommendedPrice;
    @FXML
    private Button addToMarketButton;
    @FXML
    private Button removeFromMarketButton;
    @FXML
    private ListView<String> cropsInStockView;
    @FXML
    private ListView<String> cropsForSale;
    @FXML
    private Label priceLabel;
    @FXML
    private Label quantityLabel;
    @FXML
    private Producer currentAccount;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private JFXHamburger hamburger;
    @FXML
    private JFXDrawer drawer;

    private void updateLabels(){
        int stockUpdateIndex = cropsInStockView.getSelectionModel().getSelectedIndices().get(0);
        int saleUpdateIndex = cropsForSale.getSelectionModel().getSelectedIndices().get(0);
        String quantityString, priceString;
        Product product = null;
        if (stockUpdateIndex >= 0) {
            priceString = String.valueOf(currentAccount.getProductsStored().get(stockUpdateIndex).getPrice());
            quantityString = String.valueOf(currentAccount.getProductsStored().get(stockUpdateIndex).getQuantity());
            product = currentAccount.getProductsStored().get(stockUpdateIndex).getProduct();
        }
        else if (saleUpdateIndex >= 0){
            priceString = String.valueOf(currentAccount.getProductsForSale().get(saleUpdateIndex).getPrice());
            quantityString = String.valueOf(currentAccount.getProductsForSale().get(saleUpdateIndex).getQuantity());
            product = currentAccount.getProductsForSale().get(saleUpdateIndex).getProduct();
        }else{
            priceString = "0.00";
            quantityString = "0";
        }
        priceLabel.setText(priceString);
        quantityLabel.setText(quantityString);
        if (product != null)
            recommendedPrice.setText(String.valueOf(GlobalMarket.getGlobalMarket().getRecommendedPriceFor(product)));
    }

    private void updateCrops(){
        loadCropsInStock();
        loadCropsForSale();
        updateLabels();
    }
    private void loadCropsInStock(){
        cropsInStockView.getItems().clear();
        List<Item> accountItems = currentAccount.getProductsStored();
        for (Item i : accountItems){
            cropsInStockView.getItems().add(i.getProduct().getName());
        }
    }

    private void loadCropsForSale(){
        cropsForSale.getItems().clear();
        List<Item> saleItems = currentAccount.getProductsForSale();
        for (Item i : saleItems){
            cropsForSale.getItems().add(i.getProduct().getName());
        }
    }

    private void addToMarket(){
        for (Integer index : cropsInStockView.getSelectionModel().getSelectedIndices()) {
            Item transferItem = currentAccount.transferItem(index, currentAccount.getProductsStored().get(index).getQuantity());
            currentAccount.addProductForSale(transferItem);
            Producer currentAccountProducerAdaptor = currentAccount;
            currentAccountProducerAdaptor.notifyAllSubscribers("We are now selling " + transferItem.getProduct().getName() + " at" +
                    " Php " + transferItem.getPrice());
        }
        updateCrops();
    }

    private void removeFromMarket(){
        for (Integer index : cropsForSale.getSelectionModel().getSelectedIndices()){
            currentAccount.addProductInStorage(currentAccount.transferItemFromSales(index, currentAccount.getProductsForSale().get(index).getQuantity()));
        }
        updateCrops();
    }


    private void buttonSetup(){
        addToMarketButton.setOnMouseClicked(event -> addToMarket());
        removeFromMarketButton.setOnMouseClicked(event -> removeFromMarket());
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