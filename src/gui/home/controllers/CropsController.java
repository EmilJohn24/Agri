package gui.home.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import management.Account;
import management.GlobalSessionHolder;
import management.account_types.Producer;
import market.Item;
import market.Product;

import java.util.List;

public class CropsController {
    @FXML
    private Label priceLabel;
    @FXML
    private Label quantityLabel;
    @FXML
    private ListView<String> myProductList;
    @FXML
    private Button addProductButton;
    @FXML
    private Button removeProductButton;
    @FXML
    private Button editPriceButton;
    @FXML
    private Button updateQuantityButton;

    private Producer currentAccount;

    private void updateList(){
        myProductList.getItems().clear();
        for (Item p : currentAccount.getProductsStored()){
            myProductList.getItems().add(p.getProduct().getName());
        }
    }
    @FXML
    public void initialize(){
        currentAccount = (Producer) GlobalSessionHolder.currentSession.getSessionAccount();
        updateList();
    }

    public void addProduct(MouseEvent mouseEvent) {

    }

    public void removeProduct(MouseEvent mouseEvent) {
        List<Integer> indecesRemoved = myProductList.getSelectionModel().getSelectedIndices();
        for (Integer i : indecesRemoved){
            currentAccount.getProductsStored().remove(i.intValue());
        }
        updateList();
    }

    public void editProduct(MouseEvent mouseEvent) {
    }

    public void updateQuantity(MouseEvent mouseEvent) {
    }

    public void updateContents(MouseEvent mouseEvent) {
        List<Integer> updateList =  myProductList.getSelectionModel().getSelectedIndices();
        int updateIndex = updateList.get(0);
        Item product = currentAccount.getProductsStored().get(updateIndex);
        priceLabel.setText(String.valueOf(product.getPrice()));
        quantityLabel.setText(String.valueOf(product.getProduct().getName()));

    }
}
