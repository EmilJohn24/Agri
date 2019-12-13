package gui.home.controllers;

import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import management.Account;
import management.AccountManager;
import management.GlobalSessionHolder;
import management.SubscriptionTree;
import management.account_types.Consumer;
import management.account_types.Producer;
import market.Item;
import objects.list.List;

import java.util.Collection;
import java.util.Optional;

public class FriendsController {
    public JFXListView<String> subscriptionList;
    public Label subscriptionCount;
    public JFXListView<String> saleList;
    public JFXListView<String> reservableList;
    private SubscriptionTree subscriptionTree;
    private List<Producer> temporarySubscriptionList;

    private void loadSubscriptions(){
        subscriptionList.getItems().clear();
        subscriptionCount.setText("" + GlobalSessionHolder.currentSession.getSessionAccount().getSubscriptions().size());
        for (Account acc : GlobalSessionHolder.currentSession.getSessionAccount().getSubscriptions()){
            if (acc instanceof Producer){
                subscriptionList.getItems().add(acc.getName());
                temporarySubscriptionList.add((Producer) acc);
            }
        }
    }

    public void friendAdder(MouseEvent mouseEvent) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add subscription");
        dialog.setHeaderText("Subscription");
        dialog.setContentText("Type name of company: ");
        Optional<String> result = dialog.showAndWait();

        Producer producerSubscription = subscriptionTree.get(result.get());
        if (producerSubscription != null){
            AccountManager.getAccountManager().createSubscription((Consumer) GlobalSessionHolder.currentSession.getSessionAccount(), producerSubscription);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Subscription complete");
            alert.setHeaderText("Your subscription to " + producerSubscription.getName() + " has been processed");
            alert.showAndWait();
        }
        else{
            Alert error = new Alert(Alert.AlertType.WARNING);
            error.setTitle("Producer not found");
            error.setHeaderText("Producer cannot be found");
            error.setHeaderText("The subscription cannot be processed because the account could not be found");
            error.showAndWait();
        }
        loadSubscriptions();
    }

    private List<Producer> getSelectedSubscriptions(){
        Collection<Integer> pickedItems = subscriptionList.getSelectionModel().getSelectedIndices();
        List<Producer> selectedProducers = new List<>();
        for (Integer pickedItemIndex : pickedItems){
            selectedProducers.add(temporarySubscriptionList.get(pickedItemIndex));
        }
        return selectedProducers;
    }

    public void removeFriend(MouseEvent mouseEvent) {
        for (Producer p : getSelectedSubscriptions()){
            AccountManager.getAccountManager().endSubscription((Consumer) GlobalSessionHolder.currentSession.getSessionAccount(), p);
        }
        loadSubscriptions();
    }

    public void viewSubscription(MouseEvent mouseEvent) {
        reservableList.getItems().clear();
        for (Item i : getSelectedSubscriptions().get(0).getProductsStored()){
            reservableList.getItems().add(i.getProduct().getName() + " - Php" + i.getPrice());
        }

        saleList.getItems().clear();
        for (Item i : getSelectedSubscriptions().get(0).getProductsForSale()){
            saleList.getItems().add(i.getProduct().getName() + " - Php " + i.getPrice() + " - Quantity Left: " + i.getQuantity());
        }
    }

    @FXML
    public void initialize(){
        subscriptionTree = AccountManager.getAccountManager().buildSubscriptionTree();
        temporarySubscriptionList = new List<>();
        if (GlobalSessionHolder.currentSession.getSessionAccount() instanceof Consumer){
            loadSubscriptions();
        }
    }
}
