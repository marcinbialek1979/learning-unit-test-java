package pl.mb.testing.cart;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.mb.testing.Meal;
import pl.mb.testing.order.Order;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTimeout;

@DisplayName("Cart testing")
class CartTest {

    @Test
    @DisplayName("Test 1: Simulate large order")
        //@Disabled
    void simulateLargeOrder() {
        //given
        Cart cart = new Cart();

        //when, then
        assertTimeout(Duration.ofMillis(10), () -> cart.simulateLargeOrder());
    }

    @Test
    void cartShouldNotBeEmptyAfterAddingOrderToCart() {

        //given
        Order order = new Order();
        Cart cart = new Cart();

        //when
        cart.addOrderToCart(order);

        //then

        assertThat(cart.getOrders(), anyOf(
                not(notNullValue()),
                hasSize(2),
                instanceOf(Meal.class),
                is(not(emptyCollectionOf(Order.class)))
        ));

        assertThat(cart.getOrders(), allOf(
                notNullValue(),
                hasSize(1),
                is(not(empty())),
                is(not(emptyCollectionOf(Order.class)))
        ));

        assertAll(
                () -> assertThat(cart.getOrders(), notNullValue()),
                () -> assertThat(cart.getOrders(), hasSize(1)),
                () -> assertThat(cart.getOrders(), is(not(empty()))),
                () -> assertThat(cart.getOrders().get(0).getMeals(), empty())
        );


    }


}