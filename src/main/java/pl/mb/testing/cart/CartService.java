package pl.mb.testing.cart;

import pl.mb.testing.order.OrderStatus;

public class CartService {

    private CartHandler cartHandler;

    public CartService(CartHandler cartHandler) {
        this.cartHandler = cartHandler;
    }

    Cart processCart(Cart cart) {
        if (cartHandler.canHandleCart(cart)) {
            cartHandler.sendToPrepare(cart);
            cart.getOrders().forEach(order -> order.changeOrderStatus(OrderStatus.PREPARING));
        } else {
            cart.getOrders().forEach(order -> order.changeOrderStatus(OrderStatus.REJECTED));
        }
        return cart;
    }
}
