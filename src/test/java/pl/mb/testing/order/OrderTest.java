package pl.mb.testing.order;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pl.mb.testing.Meal;
import pl.mb.testing.extension.BeforeAfterExtension;
import pl.mb.testing.order.Order;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(BeforeAfterExtension.class)
class OrderTest {

    Order order;

    @BeforeEach
    void initializeOrder() {
        System.out.println("Inside before each method");
        order = new Order();
    }

    @AfterEach
    void cleanUp() {
        System.out.println("Inside after each method");
        order.cancel();
    }

    @Test
    void mealListShouldBeEmptyAfterCreationOfOrder() {

        //when, then
        assertThat(order.getMeals(), empty());
        assertThat(order.getMeals().size(), equalTo(0));
        assertThat(order.getMeals().size(), is(0));
        assertThat(order.getMeals(), hasSize(0));
        MatcherAssert.assertThat(order.getMeals(), emptyCollectionOf(Meal.class));
    }

    @Test
    void addingMealToTheOrderShouldIncreaseOrderSize() {
        //given
        Meal meal1 = new Meal(10, "Burger");
        Meal meal2 = new Meal(5, "Cola");

        //when
        order.addMealToOrder(meal1);
        order.addMealToOrder(meal2);

        //then
        assertThat(order.getMeals(), is(not(empty())));
        assertThat(order.getMeals(), hasSize(2));
        assertThat(order.getMeals().size(), is(2));
        assertThat(order.getMeals(), contains(meal1, meal2));
        assertThat(order.getMeals(), containsInAnyOrder(meal2, meal1));

    }

    @Test
    void testIfTwoListsAreTheSame() {
        //given
        Order order1 = new Order();
        Order order2 = new Order();
        Meal meal1 = new Meal(10, "Burger");
        Meal meal2 = new Meal(5, "Cola");

        //when
        order1.addMealToOrder(meal1);
        order1.addMealToOrder(meal2);
        order2.addMealToOrder(meal1);
        order2.addMealToOrder(meal2);

        //then
        assertThat(order1.getMeals(), is(order2.getMeals()));

    }

    @Test
    void orderTotalPriceShouldNotExceedsMaxIntValue() {

        //given
        Meal meal1 = new Meal(Integer.MAX_VALUE, "Burger");
        Meal meal2 = new Meal(Integer.MAX_VALUE, "Sandwich");

        //when
        order.addMealToOrder(meal1);
        order.addMealToOrder(meal2);

        //then
        assertThrows(IllegalStateException.class, () -> order.totalPrice(), "Price limit exceeded");
    }

    @Test
    void emptyOrderTotalPriceShouldEqualToZero() {
        //given
        //when
        //Order is created in BeforeEach()

        //then
        assertEquals(0, order.totalPrice());
    }

    @Test
    void cancelingOrderShouldRemoveAllItemsFromOrderList() {
        //given
        Meal meal1 = new Meal(Integer.MAX_VALUE, "Burger");
        Meal meal2 = new Meal(Integer.MAX_VALUE, "Sandwich");
        order.addMealToOrder(meal1);
        order.addMealToOrder(meal2);
        //when
        order.cancel();
        //then
        assertTrue(order.getMeals().isEmpty());
    }


}