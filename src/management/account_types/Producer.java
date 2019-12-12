package management.account_types;

import management.Account;
import map.Point;
import market.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Producer extends Account {
    private Queue<Reservation> pendingReservations;
    private ArrayList<Item> productsForSale;
    private ArrayList<Item> productsStored;


    public Producer(String name, String passwordHash) {
        super(name, passwordHash);
        productsForSale = new ArrayList<>();
        productsStored = new ArrayList<>();
        pendingReservations = new LinkedList<>();
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
    public boolean hasReservations(){ return !pendingReservations.isEmpty(); }
    public void removeFrontReservation(){ pendingReservations.remove(); }
    public void addProductForSale(Item product){
        GlobalMarket.getGlobalMarket().addMarketItem(product);
        productsForSale.add(product);
    }

    public void addProductInStorage(Item product){ productsStored.add(product); }
    public ArrayList<Item> getProductsStored(){return productsStored;}
    public ArrayList<Item> getProductsForSale(){return productsForSale;}

    public Item getItemInSale(Product product){
        for (Item i : productsForSale){
            if (i.getProduct() == product) return i;
        }
        return null;
    }

    public Item getItemInStorage(Product product){
        for (Item i : productsStored){
            if (i.getProduct() == product) return i;
        }
        return null;
    }

    public boolean hasProductInSale(Product product){
        return getItemInSale(product) != null;
    }

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

    public boolean hasProductInStorage(Product product) {
        return getItemInStorage(product) != null;
    }
}
