package pl.mb.testing.account;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountServicesTest {

    @Test
    void getAllActiveAccounts() {
        //given
        List<Account> accounts = prepareAccountData();
        AccountRepository accountRepository = mock(AccountRepository.class);
        AccountServices accountServices = new AccountServices(accountRepository);
        when(accountRepository.getAllAccounts()).thenReturn(accounts);
        //when
        List<Account> activeAccounts = accountServices.getAllActiveAccounts();
        //then
        assertThat(activeAccounts, hasSize(2));
    }

    public List<Account> prepareAccountData() {
        Address address1 = new Address("Kwiatowa", "!/1");
        Address address2 = new Address("Prosta", "12B");
        Account account1 = new Account(address1);
        Account account2 = new Account(address2);
        Account account3 = new Account();
        return Arrays.asList(account1, account2, account3);
    }

    @Test
    void getNoActiveAccounts() {
        //given
        AccountRepository accountRepository = mock(AccountRepository.class);
        AccountServices accountServices = new AccountServices(accountRepository);
        when(accountRepository.getAllAccounts()).thenReturn(Collections.emptyList());
        //when
        List<Account> activeAccounts = accountServices.getAllActiveAccounts();
        //then
        assertThat(activeAccounts, hasSize(0));
    }
}
