package pl.mb.testing.cart;

import pl.mb.testing.Meal;
import pl.mb.testing.order.Order;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Order> orders = new ArrayList<>();

    void addOrderToCart(Order order) {
        this.orders.add(order);
    }

    public List<Order> getOrders() {
        return orders;
    }

    void clearCart() {
        this.orders.clear();
    }

    void simulateLargeOrder() {
        for (int i = 0; i < 1_000; i++) {
            Meal meal = new Meal(i % 10, "Hamburger no: " + i);
            Order order = new Order();
            order.addMealToOrder(meal);
        }
        System.out.println("Cart size: " + orders);
        clearCart();
    }
}
