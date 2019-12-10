package management.account_types;

import management.Account;

public class Farmer extends Producer {
    protected Farmer(String name, String passwordHash) {
        super(name, passwordHash);
    }

}
