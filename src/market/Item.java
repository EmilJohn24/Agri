package market;

import management.Account;
import management.account_types.Producer;

public class Item {
    private Product product;
    private Account seller;
    private Double price;
    private Integer quantity;

    public void addQuantity(Integer quantity){
        this.quantity += quantity;
    }

    public Item subtractQuantity(Integer quantity){
        this.quantity -= quantity;
        return createMarketItem(this.product, (Producer) this.seller, this.price, quantity);
    }

    //builder
    public static Item createMarketItem(Product product, Producer seller, Double price, Integer quantity){
        Item newMarketItem = new Item(product, seller, price, quantity);
        Product.addNewItem(product.getName(), newMarketItem);
        return newMarketItem;
    }

    private Item(Product product,
                 Producer seller, Double price, Integer quantity) {
        this.seller = seller;
        this.product = product;
        this.price = price;
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Account getSeller() {
        return seller;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(int q){
        this.quantity = q;
    }
}
