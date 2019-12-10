package management.account_types;

import management.Account;
import market.Market;
import market.Reservation;

import java.util.Queue;

public class Producer extends Account {
    private Queue<Reservation> pendingReservations;

    public Producer(String name, String passwordHash) {
        super(name, passwordHash);
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
}
