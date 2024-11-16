package com.message.inventory.repositories;

import com.message.inventory.model.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
    @Modifying
    @Transactional
    @Query(value = "update tblproduct set inventory_stoke = inventory_stoke + ?1 where product_id = ?2",nativeQuery = true)
    int updateStock(int stock,int id);

    @Modifying
    @Transactional
    @Query(value = "update tblproduct set sold = 0 where sold > 0",nativeQuery = true)
    void updateSold();
}
