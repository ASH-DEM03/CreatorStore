package com.ash.creatorstore.repositories;

import com.ash.creatorstore.entities.OrderItems;
import com.ash.creatorstore.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItems,Long> {
}
