package pl.mb.testing.cart;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.mb.testing.order.Order;
import pl.mb.testing.order.OrderStatus;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {
    @InjectMocks
    private CartService cartService;
    @Mock
    private CartHandler cartHandler;

    @Test
    void processCartShouldSendToPrepare() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

//        CartHandler cartHandler = mock(CartHandler.class);
//        CartService cartService = new CartService(cartHandler);
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

//        CartHandler cartHandler = mock(CartHandler.class);
//        CartService cartService = new CartService(cartHandler);
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

//        CartHandler cartHandler = mock(CartHandler.class);
//        CartService cartService = new CartService(cartHandler);
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
        //  CartHandler cartHandler = mock(CartHandler.class);
        given(cartHandler.canHandleCart(any())).willReturn(true, false, false, true);

        //when
        //then
        assertTrue(cartHandler.canHandleCart(any()));
        assertFalse(cartHandler.canHandleCart(any()));
        assertFalse(cartHandler.canHandleCart(any()));
        assertTrue(cartHandler.canHandleCart(any()));
    }

    @Test
    void processCartShouldSendToPrepareWithLambdas() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

//        CartHandler cartHandler = mock(CartHandler.class);
//        CartService cartService = new CartService(cartHandler);
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
//
//        CartHandler cartHandler = mock(CartHandler.class);
//        CartService cartService = new CartService(cartHandler);
        given(cartHandler.canHandleCart(cart)).willThrow(new IllegalArgumentException("Exception message"));

        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> cartService.processCart(cart));

    }

    @Test
    void processCartShouldSendToPrepareWithArgumentCaptor() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

//             CartHandler cartHandler = mock(CartHandler.class);
//             CartService cartService = new CartService(cartHandler);

        ArgumentCaptor<Cart> argumentCaptor = ArgumentCaptor.forClass(Cart.class);
        given(cartHandler.canHandleCart(cart)).willReturn(true);

        //when
        cartService.processCart(cart);
        //then
        //verify(cartHandler).sendToPrepare(argumentCaptor.capture());
        then(cartHandler).should().sendToPrepare(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getOrders().size(), is(1));

    }

    @Test
    void shouldDoNothingWhenProcessCard() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandler = mock(CartHandler.class);
        CartService cartService = new CartService(cartHandler);

        given(cartHandler.canHandleCart(cart)).willReturn(true);
        doNothing().when(cartHandler).sendToPrepare(cart);
        //doNothing().doThrow(IllegalStateException.class).when(cartHandler).sendToPrepare(cart);
        //willDoNothing().given(cartHandler).sendToPrepare(cart);

        //when
        Cart resultCart = cartService.processCart(cart);

        //then
       // verify(cartHandler, never()).sendToPrepare(cart);
        then(cartHandler).should().sendToPrepare(cart);
        assertThat(resultCart.getOrders(), hasSize(1));
        assertEquals(resultCart.getOrders().get(0).getOrderStatus(), OrderStatus.PREPARING);
    }

    @Test
    void shouldAnswerWhenProcessCard() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

//        CartHandler cartHandler = mock(CartHandler.class);
//        CartService cartService = new CartService(cartHandler);

       /*
       doAnswer(invocationOnMock -> {
            Cart argumentCart = invocationOnMock.getArgument(0);
            argumentCart.clearCart();
            return true;
        }).when(cartHandler).canHandleCart(cart);
        */
        when(cartHandler.canHandleCart(cart)).then(i -> {
            Cart argumentCart = i.getArgument(0);
            argumentCart.clearCart();
            return true;
        });


        //when
        Cart resultCart = cartService.processCart(cart);

        //then
        assertThat(resultCart.getOrders(), hasSize(0));
    }

    @Test
    void deliveryShouldBeFree() {
        //given
        Cart cart = new Cart();
        cart.addOrderToCart(new Order());
        cart.addOrderToCart(new Order());
        cart.addOrderToCart(new Order());

        //    CartHandler cartHandler = mock(CartHandler.class);
        given(cartHandler.isDeliveryFree(cart)).willCallRealMethod();
        //when
        //then
        assertTrue(cartHandler.isDeliveryFree(cart));
    }

}