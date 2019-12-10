package gui.home.controllers;

import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import management.GlobalSessionHolder;
import management.account_types.Producer;
import market.GlobalMarket;
import market.Item;
import market.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class CropsController {
    @FXML
    private JFXComboBox<String> selectProductCB;
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

    private HashMap<String, Product> productAccess;
    private Producer currentAccount;

    private void updateList(){
        myProductList.getItems().clear();
        for (Item p : currentAccount.getProductsStored()){
            myProductList.getItems().add(p.getProduct().getName());
        }
    }

    private void updateProductAdder(){
        productAccess.clear();
        for(Product p : GlobalMarket.getGlobalMarket().getProductList()){
            productAccess.put(p.getName(), p);
            selectProductCB.getItems().add(p.getName());
        }
    }

    private Double askForPrice(){
        TextInputDialog dialog = new TextInputDialog("0.0");
        dialog.setTitle("Price");
        dialog.setHeaderText("Price");
        dialog.setContentText("Type the price for this product");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) return Double.valueOf(result.get());
        else return 0.0;
    }
    private Integer askForQuantity(){
        TextInputDialog dialog = new TextInputDialog("0.0");
        dialog.setTitle("Quantity");
        dialog.setHeaderText("Quantity");
        dialog.setContentText("Type the new quantity for this product");

        Optional<String> result = dialog.showAndWait();
        return Integer.valueOf(result.get());
    }
    @FXML
    public void initialize(){
        productAccess = new HashMap<>();
        currentAccount = (Producer) GlobalSessionHolder.currentSession.getSessionAccount();
        updateList();
        updateProductAdder();
    }

    public void addProduct(MouseEvent mouseEvent) {
        Product productToBeAdded = productAccess.get(selectProductCB.getSelectionModel().getSelectedItem());
        currentAccount.addProductInStorage(Item.createMarketItem(productToBeAdded, currentAccount, askForPrice(), 0));
        updateList();
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
        Item product = getUpdateItem();
        product.setQuantity(askForQuantity());
    }

    public void updateContents(MouseEvent mouseEvent) {
        Item product = getUpdateItem();
        priceLabel.setText(String.valueOf(product.getPrice()));
        quantityLabel.setText(String.valueOf(product.getQuantity()));

    }

    private Item getUpdateItem() {
        List<Integer> updateList =  myProductList.getSelectionModel().getSelectedIndices();
        int updateIndex = updateList.get(0);
        return currentAccount.getProductsStored().get(updateIndex);
    }
}
