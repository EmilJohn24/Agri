package management;

public class Session {
    private Account sessionAccount;

    Session(Account acc){
        sessionAccount = acc;
    }

    public Account getSessionAccount() {
        return sessionAccount;
    }
}
