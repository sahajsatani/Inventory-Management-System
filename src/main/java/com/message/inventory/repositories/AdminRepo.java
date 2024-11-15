package com.message.inventory.repositories;

import com.message.inventory.model.Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepo extends JpaRepository<Admin,Integer> {
//    @Query(value = "select * from tbladmin where email = ?1",nativeQuery = true)
    Admin findByEmail(String email);

}
