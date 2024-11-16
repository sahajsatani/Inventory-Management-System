package com.message.inventory.repositories;

import com.message.inventory.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Integer> {
//    @Query(value = "select * from tblcustomer where email = ?1",nativeQuery = true)
    Customer findByEmail(String email);
}
