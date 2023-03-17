package pl.mb.testing.cart;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import pl.mb.testing.order.Order;
import pl.mb.testing.order.OrderStatus;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

class CartServiceTest {

    @Test
    void processCartShouldSendToPrepare() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandler = mock(CartHandler.class);
        CartService cartService = new CartService(cartHandler);
        given(cartHandler.canHandleCart(cart)).willReturn(true);

        //when
        Cart resultCart = cartService.processCart(cart);

        //then
        verify(cartHandler).sendToPrepare(cart);
        verify(cartHandler, times(1)).sendToPrepare(cart);
        verify(cartHandler, atLeastOnce()).sendToPrepare(cart);

        InOrder inOrder = inOrder(cartHandler);
        inOrder.verify(cartHandler).canHandleCart(cart);
        inOrder.verify(cartHandler).sendToPrepare(cart);

        assertThat(resultCart.getOrders(), hasSize(1));
        assertEquals(resultCart.getOrders().get(0).getOrderStatus(), OrderStatus.PREPARING);

    }

    @Test
    void processCartShouldNotSendToPrepare() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandler = mock(CartHandler.class);
        CartService cartService = new CartService(cartHandler);
        given(cartHandler.canHandleCart(cart)).willReturn(false);

        //when
        Cart resultCart = cartService.processCart(cart);

        //then
        verify(cartHandler, never()).sendToPrepare(cart);


        assertThat(resultCart.getOrders(), hasSize(1));
        assertEquals(resultCart.getOrders().get(0).getOrderStatus(), OrderStatus.REJECTED);
    }

    @Test
    void processCartShouldNotSendToPrepareWithArgumentMatchers() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandler = mock(CartHandler.class);
        CartService cartService = new CartService(cartHandler);
        //given(cartHandler.canHandleCart(any(Cart.class))).willReturn(false);
        given(cartHandler.canHandleCart(any())).willReturn(false);

        //when
        Cart resultCart = cartService.processCart(cart);
        //then
        verify(cartHandler, never()).sendToPrepare(cart);
        then(cartHandler).should(never()).sendToPrepare(any());

        assertThat(resultCart.getOrders(), hasSize(1));
        assertEquals(resultCart.getOrders().get(0).getOrderStatus(), OrderStatus.REJECTED);
    }

    @Test
    void canHandleCartShouldReturnMultipleValues() {
        /*
        given
        Order order = new Order();
        Cart cart = new Cart();
         cart.addOrderToCart(order);
        */
        //given
        CartHandler cartHandler = mock(CartHandler.class);
        given(cartHandler.canHandleCart(any())).willReturn(true, false, false, true);

        //when
        //then
        assertEquals(cartHandler.canHandleCart(any()), true);
        assertEquals(cartHandler.canHandleCart(any()), false);
        assertEquals(cartHandler.canHandleCart(any()), false);
        assertEquals(cartHandler.canHandleCart(any()), true);
    }

    @Test
    void processCartShouldSendToPrepareWithLambdas() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandler = mock(CartHandler.class);
        CartService cartService = new CartService(cartHandler);
        given(cartHandler.canHandleCart(argThat(c -> c.getOrders().size() > 0))).willReturn(true);

        //when
        Cart resultCart = cartService.processCart(cart);

        //then
        verify(cartHandler).sendToPrepare(cart);

        assertThat(resultCart.getOrders(), hasSize(1));
        assertEquals(resultCart.getOrders().get(0).getOrderStatus(), OrderStatus.PREPARING);

    }

    @Test
    void canHandleCartShouldThrowException() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandler = mock(CartHandler.class);
        CartService cartService = new CartService(cartHandler);
        given(cartHandler.canHandleCart(cart)).willThrow(new IllegalArgumentException("Exception message"));

        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> cartService.processCart(cart));

    }
}