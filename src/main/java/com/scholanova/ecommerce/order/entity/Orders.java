package com.scholanova.ecommerce.order.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scholanova.ecommerce.cart.entity.Cart;
import com.scholanova.ecommerce.cart.entity.CartItem;
import com.scholanova.ecommerce.order.exception.IllegalArgException;
import com.scholanova.ecommerce.order.exception.NotAllowedException;
import com.sun.xml.bind.v2.TODO;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

@Entity(name="orders")
public class Orders {

    @Id
    @GeneratedValue
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Column
    private String number;

    @Column
    private Date issueDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.CREATED;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="cart_id", referencedColumnName = "id")
    private Cart cart;

    public Orders(){
        /*
        Cart cart = new Cart();
        cart.setCartItems(new ArrayList<CartItem>());
        this.cart = cart;
        */
    }

    public void createOrder() throws NotAllowedException {
        Cart cart = new Cart();
        this.setCart(cart);
        this.setStatus(OrderStatus.CREATED);
    }

    public void checkout() throws NotAllowedException, IllegalArgException {
    /*
        if(this.getStatus() == OrderStatus.CLOSED){
            throw new NotAllowedException("Order closed");
        }

        if(this.getCart().getCartItems().size() == 0){
            throw new IllegalArgException("No idem in cart");
        }
    */

        Calendar calendar = Calendar.getInstance();
        java.util.Date date = calendar.getTime();
        this.issueDate = new java.sql.Date(date.getTime());
        this.status = OrderStatus.PENDING;
    }

    public void getDiscount(){
        //TODO
    }

    public void getOrderPrice(){
        //TODO
    }

    public void close(){
        //TODO
    }


    public Long getId() {return id;}

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {return number;}

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getIssueDate() {return issueDate;}

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public OrderStatus getStatus() {return status;}

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Cart getCart() {return cart;}

    public void setCart(Cart cart) throws NotAllowedException{
        if(this.getStatus() == OrderStatus.CLOSED){
            throw new NotAllowedException("Order closed");
        }
        this.cart = cart;
    }
}
