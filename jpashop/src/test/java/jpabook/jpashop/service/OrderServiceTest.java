package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    void order() {
        //given
        Member member = createMember();
        Book book = createBookItem(18, 70);
        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.ORDER, getOrder.getStatus());
        assertEquals(1, getOrder.getOrderItems().size());
        assertEquals(70 * 2, getOrder.getTotalPrice());
        assertEquals(16, book.getStockQuantity());

    }

    private Book createBookItem(int stockQuantity, int price) {
        Book book = new Book();
        book.setName("JPA Vol.1");
        book.setStockQuantity(stockQuantity);
        book.setPrice(price);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("member1");
        member.setAddress(new Address("Seoul", "Mapo", "123-123"));
        em.persist(member);
        return member;
    }

    @Test
    void inventoryExceededOrder() {
        // given
        Member member = createMember();
        Book book = createBookItem(10, 70);
        int orderCount = 12;

        // when then
        assertThrows(NotEnoughException.class,
                () -> orderService.order(member.getId(), book.getId(), orderCount));
    }

    @Test
    void cancelOrder() {
        //given
        Member member = createMember();
        Book item = createBookItem(10, 70);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order canceledOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.CANCEL, canceledOrder.getStatus());
        assertEquals(10, item.getStockQuantity());
    }
}