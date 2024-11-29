package org.example.cedc.data;

import org.example.cedc.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author hamza mustafa khan
 * @mailto : hamzamkhan@outlook.com
 */
public interface ItemRepository extends JpaRepository<Item, Long> {
}
