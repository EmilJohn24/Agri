package market;

import management.Account;
import management.account_types.Producer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

class MarketTest {
    private Market testMarket;
    private Product testProduct;
    private Account testAccount;
    private Account secondTestAccount;
    private Item testItem;
    private Item testItem2;

    public MarketTest(){
        testMarket = new Market();
        testProduct = new Product("Rice");
        testAccount = Account.generateAccount("Emil", "AAA", "Producer");
        secondTestAccount = Account.generateAccount("Teddy", "BBB", "Consumer");
        testItem = Item.createMarketItem(testProduct, (Producer) testAccount, 100.0, 0);
        testItem2 = Item.createMarketItem(testProduct, (Producer) testAccount, 200.0, 0);
    }

    @org.junit.jupiter.api.Test
    void add() {
        assert(!testMarket.isFoundIn(testProduct));
        testMarket.addIfAbsent(testProduct);
        assert(testMarket.isFoundIn(testProduct));
    }

    @org.junit.jupiter.api.Test
    void remove() {
        add();
        assert(testMarket.isFoundIn(testProduct));
        testMarket.remove(testProduct);
        assert(!testMarket.isFoundIn(testProduct));
    }

    @org.junit.jupiter.api.Test
    void addMarketItem() {
        testMarket.addMarketItem(testItem);
        testMarket.addMarketItem(testItem2);
        LinkedList<Item> sales = testMarket.getMarketList(testProduct);
        assert(sales.get(0).getProduct() == testProduct && sales.get(0).getSeller() == testAccount);
    }

    @org.junit.jupiter.api.Test
    void removeMarketItem() {
        addMarketItem();
        LinkedList<Item> sales = testMarket.getMarketList(testProduct);
        assert(sales.size() == 2);
        testMarket.removeMarketItem(testItem);
        testMarket.removeMarketItem(testItem2);
        assert(sales.size() == 0);
    }

    @org.junit.jupiter.api.Test
    void addReservation() {
        Reservation newReservation = new Reservation(testAccount, secondTestAccount, LocalDate.of(2019,
                4, 24), testProduct, 1000);
        testMarket.addReservation(newReservation);
        ArrayList<Reservation> reservations = testMarket.getReservationsBy(testAccount);
        assert(reservations.get(0) == newReservation);
    }

    @org.junit.jupiter.api.Test
    void removeReservation() {
    }

    @org.junit.jupiter.api.Test
    void getReservationsBy() {
    }
}