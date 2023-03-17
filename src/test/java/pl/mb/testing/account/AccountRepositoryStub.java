package pl.mb.testing.account;

import java.util.Arrays;
import java.util.List;

public class AccountRepositoryStub implements AccountRepository {

    @Override
    public List<Account> getAllAccounts() {
        Address address1 = new Address("Kwiatowa", "!/1");
        Address address2 = new Address("Prosta", "12B");
        Account account1 = new Account(address1);
        Account account2 = new Account(address2);
        Account account3 = new Account();

        return Arrays.asList(account1, account2, account3);
    }
}
