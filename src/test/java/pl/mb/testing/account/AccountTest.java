package pl.mb.testing.account;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import pl.mb.testing.account.Account;
import pl.mb.testing.account.Address;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumingThat;


class AccountTest {

    @Test
    public void newlyCreatedAccountShouldBeNotActive() {
        //given
        Account account = new Account();
        //when
        boolean accountStatus = account.isActive();
        //then
        assertFalse(account.isActive());
        assertThat(accountStatus, is(false));
    }

    @Test
    public void accountShouldBeActiveAfterActivation() {
        //given
        Account account = new Account();
        //when
        account.activate();
        boolean accountStatus = account.isActive();
        //then
        assertTrue(account.isActive());
        assertThat(accountStatus, is(equalTo(true)));

    }

    @Test
    void newlyCreatedAccountShouldNotHaveDefaultDeliveryAddressSet() {
        //given
        Account account = new Account();

        //when
        Address address = account.getDefaultDeliveryAddress();

        //then
        assertNull(address);
        assertThat(address, nullValue());
    }

    @Test
    void defaultDeliveryAddressShouldNotBeNullAfterBeingSet() {
        //given
        Account account = new Account();
        Address address = new Address("Nowa", "1/1a");
        account.setDefaultDeliveryAddress(address);

        //when, then
        assertNotNull(address);
        assertThat(address, notNullValue());
    }

    @Test
    @RepeatedTest(10)
    void accountShouldBeActiveWhenDeliveryAddressIsNotNull() {
        //given
        Address address = new Address("Grzybowa", "1/10");
        //when
        Account account = new Account(address);
        //then
        assumingThat(address != null, () -> assertTrue(account.isActive()));

    }

    @Test
    void invalidEmailShouldThrowException() {
        // given
        Account account = new Account();
        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> account.setEmail("Incorrect e-mail"), "Wrong e-mail address");

    }
}
