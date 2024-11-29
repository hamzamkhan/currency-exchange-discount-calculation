package org.example.cedc.data;

import org.example.cedc.model.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author hamza mustafa khan
 * @mailto : hamzamkhan@outlook.com
 */

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
