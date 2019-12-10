package management;

import management.account_types.Consumer;
import management.account_types.Producer;
import market.Reservation;

import java.util.ArrayList;
import java.util.Queue;

public class Account {
    private ArrayList<Account> friends;
    private ArrayList<Session> sessions;
    private String name;
    private String passwordHash;


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
        this.friends = new ArrayList<>();
        this.sessions = new ArrayList<>();

    }


    public void addFriend(Account acc){
        friends.add(acc);
    }
    public void removeFriend(Account acc) {  friends.remove(acc); }


    public final ArrayList<Account> getFriends(){
        return friends;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
