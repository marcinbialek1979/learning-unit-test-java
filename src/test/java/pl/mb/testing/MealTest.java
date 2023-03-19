package pl.mb.testing;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import pl.mb.testing.extension.IAExceptionIgnoreExtension;
import pl.mb.testing.order.Order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class MealTest {

    @Test
    void shouldReturnDiscountedPrice() {
        //given
        Meal meal = new Meal(50);

        //when
        int discountPrice = meal.getDiscountPrice(10);

        //then
        assertEquals(40, discountPrice);
        assertThat(discountPrice, equalTo(40));
    }

    @Test
    void referencesToTheSameObjectShouldReturnTrue() {
        //given
        Meal meal1 = new Meal(50);
        Meal meal2 = meal1;

        //then
        assertSame(meal1, meal2);
        assertThat(meal1, sameInstance(meal2));
    }

    @Test
    void referencesToTheOtherObjectShouldReturnFalse() {
        //given
        Meal meal1 = new Meal(50);
        Meal meal2 = new Meal(50);

        //then
        assertNotSame(meal1, meal2);
        assertThat(meal1, is(not(sameInstance(meal2))));
    }

    @Test
    void twoMealsShouldBeEqualsWhenPriceAndNameAreTheSame() {
        //given
        Meal meal1 = new Meal(50, "pizza");
        Meal meal2 = new Meal(50, "pizza");

        //then
        assertEquals(meal1, meal2);
        assertThat(meal1, equalTo(meal2));
    }

    @Test
    void twoMealsShouldBeNotEqualsWhenPriceAndNameAreNotTheSame() {
        //given
        Meal meal1 = new Meal(50, "pizza");
        Meal meal2 = new Meal(51, "pizza");

        //then
        assertNotEquals(meal1, meal2);
        assertThat(meal1, not(equalTo(meal2)));
    }

    @Test
    void exceptionShouldBeThrowIfDiscountIsHigherThenThePrice() {
        //given
        Meal meal1 = new Meal(50, "pizza");
        //when, then
        assertThrows(IllegalArgumentException.class, () -> meal1.getDiscountPrice(55));
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 10, 15, 19})
    void mealPricesShouldBeLowerThen20(int price) {
        assertThat(price, lessThan(20));
    }


    @ParameterizedTest
    @MethodSource("createMealsWihNameAndPrice")
    void burgersShouldHaveCorrectNameAndPrice(String name, int price) {
        assertThat(name, containsString("burger"));
        assertThat(price, greaterThanOrEqualTo(10));
    }

    private static Stream<Arguments> createMealsWihNameAndPrice() {
        return Stream.of(Arguments.of("Hamburger", 10), Arguments.of("Cheeseburger", 20));
    }

    @ParameterizedTest
    @MethodSource("createCakeNames")
    void cakeNamesShouldEndWithCake(String name) {
        assertThat(name, endsWith("cake"));
    }

    private static Stream<String> createCakeNames() {
        List<String> cakeNames = Arrays.asList("Cheesecake", "Fruitcake", "Cupcake");
        return cakeNames.stream();
    }

    @ExtendWith(IAExceptionIgnoreExtension.class)
    @ParameterizedTest
    @ValueSource(ints = {1, 3, 6, 9})
    void mealPricesShouldBeLowerThen10(int price) {
        if (price > 5) {
            throw new IllegalArgumentException();
        }
        assertThat(price, lessThan(9));
    }

//    @TestFactory
//    Collection<DynamicTest> dynamicTestCollections() {
//        return Arrays.asList(
//                dynamicTest("Dynamic test 1", () -> assertThat(5, lessThan(6))),
//                dynamicTest("Dynamic test 2", () -> assertEquals(10, (2 * 5))));
//    }


    @TestFactory
    Collection<DynamicTest> calculateMealPrices() {
        Order order = new Order();
        order.addMealToOrder(new Meal(10, 2, "Hamburger"));
        order.addMealToOrder(new Meal(7, 4, "Fries"));
        order.addMealToOrder(new Meal(22, 3, "Pizza"));

        Collection<DynamicTest> dynamicTests = new ArrayList<>();

        for (int i = 0; i < order.getMeals().size(); i++) {
            int price = order.getMeals().get(i).getPrice();
            int quantity = order.getMeals().get(i).getQuantity();

            String name = "Test number: " + (i + 1);
            Executable executable = () -> assertThat(calculatePrice(price, quantity), lessThan(67));
            DynamicTest dynamicTest = DynamicTest.dynamicTest(name, executable);

            dynamicTests.add(dynamicTest);
        }
        return dynamicTests;
    }

    @Test
    void testMealSumPrice() {
        //given
        Meal meal = mock(Meal.class);
        given(meal.getPrice()).willReturn(15);
        given(meal.getQuantity()).willReturn(3);

        given(meal.sumPrice()).willCallRealMethod();

        //when
        int sumPrice = meal.sumPrice();
        //then
        assertEquals(45, sumPrice);
    }

    private int calculatePrice(int price, int quantity) {
        return price * quantity;
    }


}