package pl.mb.testing.account;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

class AccountServicesStubTest {

    @Test
    void getAllActiveAccounts() {
        //given
        AccountRepository accountRepositoryStub = new AccountRepositoryStub();
        AccountServices accountServices = new AccountServices(accountRepositoryStub);
        //when
        List<Account> activeAccounts = accountServices.getAllActiveAccounts();
        //then
        assertThat(activeAccounts, hasSize(2));
    }
}