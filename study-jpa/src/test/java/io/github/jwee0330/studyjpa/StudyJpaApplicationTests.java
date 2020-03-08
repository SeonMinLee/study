package io.github.jwee0330.studyjpa;

import io.github.jwee0330.studyjpa.domain.Order;
import io.github.jwee0330.studyjpa.domain.OrderItem;
import io.github.jwee0330.studyjpa.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class StudyJpaApplicationTests {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void orderQueryTest() {

        List<Order> allWithMemberDeliveryInnerJoin = orderRepository.findAllWithMemberDeliveryInnerJoin();

        for (Order order : allWithMemberDeliveryInnerJoin) {
            order.getOrderItems().stream().map(OrderItem::getOrderPrice);
        }

        System.err.println("------------------------");

        List<Order> allWithMemberDeliveryJoinFetch = orderRepository.findAllWithMemberDeliveryJoinFetch();

        for (Order order : allWithMemberDeliveryJoinFetch) {
            order.getOrderItems().stream().map(OrderItem::getOrderPrice);
        }
    }

}
