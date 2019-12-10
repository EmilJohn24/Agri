package management.account_types;

import management.Account;
import market.Market;
import market.Product;
import market.Reservation;

import java.util.ArrayList;
import java.util.Queue;

public class Producer extends Account {
    private Queue<Reservation> pendingReservations;
    private ArrayList<Product> productsForSale;

    public Producer(String name, String passwordHash) {

        super(name, passwordHash);
        productsForSale = new ArrayList<>();
    }

    public void requestReservation(Reservation reservation){
        pendingReservations.add(reservation);
    }

    public void addFrontReservationTo(Market market){
        market.addReservation(pendingReservations.remove());
    }

    public Reservation peekFrontReservation(){
        return pendingReservations.peek();
    }

    public void addProductForSale(Product product){
        productsForSale.add(product);
    }
}
