package management;

import management.account_types.Consumer;
import management.account_types.Producer;

import java.util.Hashtable;

//singleton model
public class AccountManager {

    static private AccountManager globalAccManager;
    static public AccountManager getAccountManager(){
        return globalAccManager;
    }
    static public final String CONSUMER_ID = "Consumer";
    static public final String PRODUCER_ID = "Producer";

    static private AccountManager setupAccountManager(){
        //setup from file possibly
        AccountManager newAccountManager = new AccountManager();
//        newAccountManager.addUser("admin", "admin");
        return newAccountManager;
    }

    static{
        globalAccManager = setupAccountManager();
    }
    private Hashtable<String, Account> accounts;

    private AccountManager(){
        accounts = new Hashtable<>();
    }

    public Account addUser(String username, String password, String typeID){
        Account newAccount = Account.generateAccount(username, password, typeID);
        accounts.put(username, newAccount);
        return newAccount;
    }

    public Account fetchAccount(String username){
        return accounts.get(username);
    }

    public boolean endFriendship(Account initializer, Account removedFriend){
        if (removedFriend != null){
            initializer.removeFriend(removedFriend);
            removedFriend.removeFriend(initializer);
            return true;
        }
        return false;
    }

    public boolean createSubscription(Consumer initializer, String friendName){
        Account subscribedToAccount = accounts.get(friendName);
        if (subscribedToAccount instanceof Producer)  return createSubscription(initializer, (Producer) subscribedToAccount);
        else return false;
    }

    public boolean createSubscription(Consumer initializer, Producer potentialFriend){
        if (potentialFriend != null){
            initializer.addFriend(potentialFriend);
            potentialFriend.addFriend(initializer);
            return true;
        }
        return false;
    }

    public Session login(String name, String password){
        Account accountQuery = accounts.get(name);
        if (accountQuery == null) return null;
        return accountQuery.login(password);
    }

}
