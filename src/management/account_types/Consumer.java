package management.account_types;

import management.Account;
import market.Item;
import market.Product;

import java.util.ArrayList;

public class Consumer extends Account {
    private ArrayList<Item> itemsBought;
    public Consumer(String name, String passwordHash) {
        super(name, passwordHash);
        itemsBought = new ArrayList<>();
    }
    public void addItemBought(Item item){
        itemsBought.add(item);
    }

    public final ArrayList<Item> getItemsBought(){
        return itemsBought;
    }
}
