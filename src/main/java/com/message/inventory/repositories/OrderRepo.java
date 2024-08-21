package com.message.inventory.repositories;

import com.message.inventory.model.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<Order,Integer> {
    @Override
    Optional<Order> findById(Integer integer);
}
