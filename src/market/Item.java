package market;

import management.Account;
import management.account_types.Producer;

public class Item {
    private Product product;
    private Account seller;
    private Double price;

    //builder
    public static Item createMarketItem(Product product, Producer seller, Double price){
        Item newMarketItem = new Item(product, seller, price);
        Product.addNewItem(product.getName(), newMarketItem);
        seller.addProductForSale(product);
        return newMarketItem;
    }

    private Item(Product product,
                 Producer seller, Double price) {
        this.seller = seller;
        this.product = product;
        this.price = price;
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
}
