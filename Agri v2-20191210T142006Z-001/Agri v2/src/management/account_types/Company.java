package management.account_types;

import management.Account;

public class Company extends Producer {
    protected Company(String name, String passwordHash){
        super(name, passwordHash);
    }
}
