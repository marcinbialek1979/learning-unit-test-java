package pl.mb.testing.account;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class AddressTest {
    @ParameterizedTest
    @CsvSource({"Fabryczna, 10", "Armii Krajowej, 57/11", "'Romka, Tomka, Atomka', 10/10"})
    void givenAddressesShouldBeNotEmptyAndHaveProperNames(String street, String number){
        assertThat(street, is(notNullValue()));
        assertThat(street, containsString("a"));
        assertThat(number, is(notNullValue()));
        assertThat(number.length(), is(lessThan(8)));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/pl/mb/testing/addresses.csv")
    void addressesFromFileShouldBeNotEmptyAndHaveProperNames(String street, String number){
        assertThat(street, is(notNullValue()));
        assertThat(street, containsString("a"));
        assertThat(number, is(notNullValue()));
        assertThat(number.length(), is(lessThan(8)));
    }
}