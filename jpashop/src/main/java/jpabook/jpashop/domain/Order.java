package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "orders")
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    //enum(ORDER, CANCEL)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    //==Association Assist Methods==//
    // Member Entity
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    // OrderItem Entity
    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    // Delivery Entity
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //==Business Logic Methods==//
    /**
     * Cancel Order
     */
    public void cancel() {
        // Validate Delivery Status
        if(delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("Order cannot be canceled once they are delivered.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for(OrderItem orderItem: orderItems) {
            orderItem.cancel();
        }
    }

    /**
     * Retrieve Total Order Price
     */
    public int getTotalPrice() {
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }

        return totalPrice;
    }

    //==Create Methods=//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.ORDER);

        for(OrderItem orderItem: orderItems) {
            order.addOrderItem(orderItem);
        }

        return order;
    }
}
