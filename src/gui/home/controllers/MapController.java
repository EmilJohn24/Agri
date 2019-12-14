package gui.home.controllers;

import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import management.GlobalSessionHolder;
import management.account_types.Consumer;
import market.Item;

public class MapController {

    public JFXListView<String> transactionList;

    @FXML
    private void initialize(){
        Consumer consumerAdapterAccount = (Consumer) GlobalSessionHolder.currentSession.getSessionAccount();
        for (Item transaction : consumerAdapterAccount.getItemsBought()){
            transactionList.getItems().add(transaction.getSeller().getName() + " - " + transaction.getQuantity() + " kg -"
                                        + transaction.getProduct().getName() + " - " + (transaction.getPrice() * transaction.getQuantity()));
        }
    }
}
