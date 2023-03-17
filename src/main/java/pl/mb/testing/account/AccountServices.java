package pl.mb.testing.account;

import java.util.List;
import java.util.stream.Collectors;

public class AccountServices {

    AccountRepository accountRepository;

    public AccountServices(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAllActiveAccounts() {
        return accountRepository.getAllAccounts()
                .stream()
                .filter(Account::isActive)
                .collect(Collectors.toList());
    }
}
