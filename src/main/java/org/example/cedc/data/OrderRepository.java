package org.example.cedc.data;

import org.example.cedc.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author hamza mustafa khan
 * @mailto : hamzamkhan@outlook.com
 */

public interface OrderRepository extends JpaRepository<Order, Long> {
}
