package management.account_types;

import management.Account;

public class Consumer extends Account {
    public Consumer(String name, String passwordHash) {
        super(name, passwordHash);
    }
}
