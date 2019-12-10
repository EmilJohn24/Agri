package gui.home.controllers;

import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import management.AccountManager;

public class RegistrationController {
    @FXML
    public PasswordField repasswordText;
    @FXML
    public PasswordField passwordText;
    @FXML
    public TextField usernameText;
    @FXML
    private JFXComboBox<String> typeBox;

    @FXML
    public void initialize(){
        initializeTypeBox();
    }

    private void initializeTypeBox() {
        typeBox.getItems().add(AccountManager.PRODUCER_ID);
        typeBox.getItems().add(AccountManager.CONSUMER_ID);
    }

    public void submit(MouseEvent mouseEvent) {
        if (passwordText.getText().equals(repasswordText.getText())){
            AccountManager.getAccountManager().addUser(usernameText.getText(), passwordText.getText(), typeBox.getValue());
            loadSignUpSuccess();
        }
        else{
            repasswordText.setStyle("-fx-background-color: RED;");
        }
    }

    private void loadSignUpSuccess(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Your account has been created");
        alert.setContentText("Log in to continue");

        alert.showAndWait();
    }
}
