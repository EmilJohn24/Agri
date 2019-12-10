package market;

import java.util.Hashtable;
import java.util.LinkedList;

public class Product {
    static private Hashtable<String, LinkedList<Item>> marketItems;

    static{
        marketItems = new Hashtable<>();
    }

    public static void addNewItem(String name, Item item){
        marketItems.putIfAbsent(name, new LinkedList<>());
        marketItems.get(name).addFirst(item);
    }


    private String name;

    public Product(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEqual(Product product){
        return product.name.equals(this.name);
    }
}
