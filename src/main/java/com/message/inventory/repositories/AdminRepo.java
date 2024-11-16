package com.message.inventory.repositories;

import com.message.inventory.model.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepo extends JpaRepository<Admin,Integer> {
//    @Query(value = "select * from tbladmin where email = ?1",nativeQuery = true)
    Admin findByEmail(String email);

}
