package management;

import management.account_types.Consumer;
import management.account_types.Producer;
import map.Point;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private List<Account> subscriptions;
    private List<Session> sessions;
    private String name;
    private String passwordHash;
    private Point farmLocation;


    private static String hash(String password){
        return Integer.toHexString(password.hashCode());
    }

    public static Account generateAccount(String name, String password, String typeID){
        if (typeID.equals(AccountManager.CONSUMER_ID)) return new Consumer(name, hash(password));
        else if (typeID.equals(AccountManager.PRODUCER_ID)) return new Producer(name, hash(password));
        return null;
    }

    public Session login(String password){
        if (hash(password).equals(this.passwordHash)) return new Session(this);
        else return null;
    }

    protected Account(String name, String passwordHash){
        this.name = name;
        this.passwordHash = passwordHash;
        this.subscriptions = new ArrayList<>();
        this.sessions = new ArrayList<>();

    }


    public void addFriend(Account acc){
        subscriptions.add(acc);
    }
    public void removeFriend(Account acc) {  subscriptions.remove(acc); }


    public final List<Account> getSubscriptions(){
        return subscriptions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Point getFarmLocation() {
        return farmLocation;
    }


    public void setFarmLocation(Point farmLocation) {
        this.farmLocation = farmLocation;
        this.farmLocation.setOwner(this);
    }
}
