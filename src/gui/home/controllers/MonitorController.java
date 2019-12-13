package gui.home.controllers;

import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import management.GlobalSessionHolder;
import management.account_types.Producer;
import market.Reservation;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class MonitorController {
    public ImageView cropImage;
    public ListView<String> reservationTable;
    private Reservation choosenReservation;
    private Producer account;

    public void onClickImageView() throws IOException {
        final JFileChooser fileChooser = new JFileChooser();
        int statusValue = fileChooser.showOpenDialog(null);
        if (statusValue == JFileChooser.APPROVE_OPTION) {
            File selectedImage = fileChooser.getSelectedFile();
            String directory =  selectedImage.getName();
            Files.copy(selectedImage.toPath(),
                    new File("./" + directory).toPath());
            choosenReservation.setImage(new Image("file:" + "./" + directory));
            cropImage.setImage(choosenReservation.getImage());
        }
    }

    public void onChoose(){
        Integer choice = reservationTable.getSelectionModel().getSelectedIndices().get(0);
        choosenReservation = account.getOfficialReservations().get(choice);
        if (choosenReservation != null){
            if (choosenReservation.getImage() != null){
                cropImage.setImage(choosenReservation.getImage());
            }
        }
    }

    public void loadTable(){
        reservationTable.getItems().clear();
        account = (Producer) GlobalSessionHolder.currentSession.getSessionAccount();
        for (Reservation reservation : account.getOfficialReservations()){
            reservationTable.getItems().add(reservation.getBuyer().getName() + " - " +
                    reservation.getProduct().getName() + " - " + reservation.getAmount() + " - " +
                    reservation.getDate().toString());
        }
    }

    public void initialize(){
        if (GlobalSessionHolder.currentSession.getSessionAccount() instanceof Producer){
            loadTable();
        }
    }
}
