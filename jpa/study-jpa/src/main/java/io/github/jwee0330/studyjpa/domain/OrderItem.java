package io.github.jwee0330.studyjpa.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "order_item")
@Getter
@Setter
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item; //주문 상품
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order; //주문
    private int orderPrice; //주문 가격 private int count;

    public static OrderItem createOrderItem(Book book, int price, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(book);
        orderItem.setOrderPrice(price * count);
        return orderItem;
    }
}

