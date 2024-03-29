package management.account_types;

import management.Account;
import market.*;
import objects.list.List;
import objects.queue.Queue;


public class Producer extends Account {
    private Queue<Reservation> pendingReservations;
    private List<Item> productsForSale;
    private List<Item> productsStored;
    private List<Reservation> officialReservations;

    public List<Reservation> getOfficialReservations(){
        return officialReservations;
    }

    public Producer(String name, String passwordHash) {
        super(name, passwordHash);
        productsForSale = new List<>();
        productsStored = new List<>();
        pendingReservations = new Queue<>();
        officialReservations = new List<>();
    }

    public void notifyAllSubscribers(String text){
        for (Account subscribers : getSubscriptions()){
            if (subscribers instanceof Consumer){
                Consumer subscriber = (Consumer) subscribers;
                subscriber.addNotification(this.getName() + " : " + text);
            }
        }
    }
    public void requestReservation(Reservation reservation){
        pendingReservations.push(reservation);
    }
    public void addFrontReservationTo(Market market){
        Reservation reservation = pendingReservations.pop();
        market.addReservation(reservation);
        officialReservations.add(reservation);
    }
    public Reservation peekFrontReservation(){
        return pendingReservations.front();
    }
    public boolean hasReservations(){ return !pendingReservations.empty(); }
    public void removeFrontReservation(){ pendingReservations.pop(); }
    public void addProductForSale(Item product){
        GlobalMarket.getGlobalMarket().addMarketItem(product);
        productsForSale.add(product);
    }

    public void addProductInStorage(Item product){ productsStored.add(product); }
    public List<Item> getProductsStored(){return productsStored;}
    public List<Item> getProductsForSale(){return productsForSale;}

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
