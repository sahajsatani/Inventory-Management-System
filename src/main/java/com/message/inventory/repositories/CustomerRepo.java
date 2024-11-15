package com.message.inventory.repositories;

import com.message.inventory.model.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Integer> {
//    @Query(value = "select * from tblcustomer where email = ?1",nativeQuery = true)
    Customer findByEmail(String email);
}
