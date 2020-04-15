package com.scholanova.ecommerce.order.entity;

import com.scholanova.ecommerce.cart.entity.Cart;
import com.scholanova.ecommerce.cart.entity.CartItem;
import com.scholanova.ecommerce.cart.exception.CartException;
import com.scholanova.ecommerce.order.exception.IllegalArgException;
import com.scholanova.ecommerce.order.exception.NotAllowedException;
import com.scholanova.ecommerce.product.entity.Product;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class OrdersTest {

    @Test
    //@Disabled
    public void checkout_ShouldSetTheDateAndTimeOfTodayInTheOrder() throws NotAllowedException, IllegalArgException {
        //given
        Orders order = new Orders();
        Calendar calendar = Calendar.getInstance();
        java.util.Date dateOfToday = calendar.getTime();
        java.sql.Date date = new java.sql.Date(dateOfToday.getTime());
        //when
        order.checkout();
        //then
        assertThat(order.getIssueDate()).isEqualTo(date);


    }

    @Test
    //@Disabled
    public void checkout_ShouldSetOrderStatusToPending() throws NotAllowedException, IllegalArgException {
        //given
        Orders order = new Orders();
        //when
        order.checkout();
        //then
        assertThat(order.getStatus()).isEqualTo(OrderStatus.PENDING);
    }

    @Test
    @Disabled
    public void checkout_ShouldThrowNotAllowedExceptionIfStatusIsClosed(){
        //given
        Orders order = new Orders();
        //when
        order.setStatus(OrderStatus.CLOSED);
        //then
        assertThrows(NotAllowedException.class, () -> order.checkout());
    }

    @Test
    @Disabled
    public void checkout_ShouldThrowIllegalArgExceptionIfCartTotalItemsQuantityIsZERO(){
        //given
        Orders order = new Orders();
        /*
        Cart cart = new Cart();
        cart.setCartItems(new ArrayList<CartItem>());
        order.setCart(cart);
        */

        //then
        assertThrows(IllegalArgException.class, () -> order.checkout());

    }

    @Test
    //@Disabled
    public void setCart_ShouldThrowNotAllowedExceptionIfStatusIsClosed(){
        //given
        Orders order = new Orders();
        Cart cart = new Cart();
        //when
        order.setStatus(OrderStatus.CLOSED);
        //then
        assertThrows(NotAllowedException.class, () -> order.setCart(cart));
    }

    @Test
    //@Disabled
    public void createOrder_ShouldSetTheCartInTheOrder() throws NotAllowedException {
        //given
        Orders order = new Orders();
        //when
        order.createOrder();
        //then
        assertThat(order.getCart()).isNotNull();
    }

    @Test
    //@Disabled
    public void createOrder_ShouldSetStatusToCreated() throws NotAllowedException {
        //given
        Orders order = new Orders();
        //when
        order.createOrder();
        //then
        assertThat(order.getStatus()).isEqualTo(OrderStatus.CREATED);
    }

    @Test
    //@Disabled
    public void getDiscount_shouldReturnZEROIFCartTotalPriceIsLessThan100() throws NotAllowedException {
        //given
        Orders order = new Orders();
        order.createOrder();
        double discount;
        //when
        discount = order.getDiscount();
        //then
        assertThat(discount).isEqualTo(0);
    }

    @Test
    //@Disabled
    public void getDiscount_shouldReturn5percentIfCartTotalPriceIsMoreOrEqual100() throws NotAllowedException {
        //given
        Orders order = new Orders();
        order.createOrder();
        Product ps5 =  Product.create("ps5","sony next gen home console",500f,0f,"euro");
        order.getCart().addProduct(ps5,2);
        double discount;
        //when
        discount = order.getDiscount();
        //then
        assertThat(discount).isEqualTo(0.05);
    }

    @Test
    //@Disabled
    public void getOrderPrice_shouldReturnTotalPriceWithDiscount() throws NotAllowedException {
        //given
        Orders order = new Orders();
        order.createOrder();
        Product ps5 =  Product.create("ps5","sony next gen home console",500f,0f,"euro");
        order.getCart().addProduct(ps5,2);
        //when

        //then
        assertThat(order.getOrderPrice()).isEqualTo(950);
    }

    @Test
    //@Disabled
    public void close_ShouldSetStatusToClose(){
        //given
        Orders order = new Orders();
        //when
        order.close();
        //then
        assertThat(order.getStatus()).isEqualTo(OrderStatus.CLOSED);

    }

}