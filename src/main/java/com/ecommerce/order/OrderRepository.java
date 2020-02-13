package com.ecommerce.order;

import com.ecommerce.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
//    @Query("SELECT o FROM Order o JOIN FETCH o.items WHERE o.id = (:id)")
//    public Order findOneWithItems(@Param("id") Long id);
}
