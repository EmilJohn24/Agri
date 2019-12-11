package management.account_types;

import management.Account;
import market.*;

import java.util.ArrayList;
import java.util.Queue;

public class Producer extends Account {
    private Queue<Reservation> pendingReservations;
    private ArrayList<Item> productsForSale;
    private ArrayList<Item> productsStored;


    public Producer(String name, String passwordHash) {
        super(name, passwordHash);
        productsForSale = new ArrayList<>();
        productsStored = new ArrayList<>();
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
    public void addProductForSale(Item product){
        GlobalMarket.getGlobalMarket().addMarketItem(product);
        productsForSale.add(product);
    }
    public void addProductInStorage(Item product){ productsStored.add(product); }
    public ArrayList<Item> getProductsStored(){return productsStored;}
    public ArrayList<Item> getProductsForSale(){return productsForSale;}
    public Item transferItemFromSales(int index, int count){
        Item transferItem = productsForSale.get(index);
        Item backItem = transferItem.subtractQuantity(count);
        GlobalMarket.getGlobalMarket().removeMarketItem(transferItem);
        if (transferItem.getQuantity() <= 0) productsForSale.remove(index);
        return backItem;
    }
    public Item transferItem(int index, int count){
        Item transferItem = productsStored.get(index);
        Item newItem = transferItem.subtractQuantity(count);
        if (transferItem.getQuantity() <= 0) productsStored.remove(index);
        return newItem;
    }
}
