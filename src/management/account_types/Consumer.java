package management.account_types;

import management.Account;
import market.Item;
import market.Product;
import objects.list.List;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Consumer extends Account {
    private List<Item> itemsBought;
    private Queue<String> notifications;

    public String getNextNotification(){
        return notifications.remove();
    }

    public boolean hasNotifications(){
        return !notifications.isEmpty();
    }

    public void addNotification(String notification){
        notifications.add(notification);
    }
    public Consumer(String name, String passwordHash) {
        super(name, passwordHash);
        itemsBought = new List<>();
        notifications = new LinkedList<>();
    }
    public void addItemBought(Item item){
        itemsBought.add(item);
    }

    public final List<Item> getItemsBought(){
        return itemsBought;
    }
}
