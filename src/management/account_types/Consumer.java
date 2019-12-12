package management.account_types;

import management.Account;
import market.Item;
import market.Product;
import objects.list.List;

import java.util.ArrayList;

public class Consumer extends Account {
    private List<Item> itemsBought;
    public Consumer(String name, String passwordHash) {
        super(name, passwordHash);
        itemsBought = new List<>();
    }
    public void addItemBought(Item item){
        itemsBought.add(item);
    }

    public final List<Item> getItemsBought(){
        return itemsBought;
    }
}
