package io.github.jwee0330.studyjpa.repository;

import io.github.jwee0330.studyjpa.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;
    //OrderRepository 추가 코드
    public List<Order> findAllWithMemberDeliveryJoinFetch() {
        return em.createQuery(
                "select o from Order o" +
                        " join fetch o.member m" +
                        " join fetch o.delivery d", Order.class).getResultList();
    }

    public List<Order> findAllWithMemberDeliveryInnerJoin() {
        return em.createQuery(
                "select o from Order o" +
                        " inner join o.member m" +
                        " inner join o.delivery d", Order.class)
                .getResultList();
    }
}
