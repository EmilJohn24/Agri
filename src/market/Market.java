package market;

import management.Account;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedList;

public class Market {
    private Hashtable<Product, LinkedList<Item>> sales;
    private LinkedList<Reservation> officialReservations;

    Market(){
        sales = new Hashtable<>();
        officialReservations = new LinkedList<>();
    }

    public LinkedList<Item> getMarketList(Product product){
        return this.sales.get(product);
    }

    public LinkedList<Product> getProductList(){ return new LinkedList<>(this.sales.keySet()); }

    public void addIfAbsent(Product newProduct){
        sales.putIfAbsent(newProduct, new LinkedList<>());
    }

    public void remove(Product removedProduct){
        sales.remove(removedProduct);
    }

    public void addMarketItem(Item item){
        Product itemProduct = item.getProduct();
        addIfAbsent(itemProduct);
        getMarketList(itemProduct).add(item);
    }


    public void addMarketItem(Product product, Item item){
        getMarketList(product).add(item);
        item.setProduct(product);
    }

    public void removeMarketItem(Item item){
        getMarketList(item.getProduct()).remove(item);
    }

    public boolean isFoundIn(Product product){
        return sales.keySet().contains(product);
    }

    //TODO:reservation stuff: consider extracting to class
    public void addReservation(Reservation newReservation){
        officialReservations.add(newReservation);
    }
    public void removeReservation(Reservation reservation){ officialReservations.remove(reservation);  }
    public ArrayList<Reservation> getReservationsBy(Account acc){
        ArrayList<Reservation> reservations = new ArrayList<>();
        for (Reservation r : officialReservations){
            if (r.getSeller() == acc) reservations.add(r);
        }
        return reservations;
    }





}
