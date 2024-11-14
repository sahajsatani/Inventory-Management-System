package com.message.inventory.Jwts;

import com.message.inventory.model.Entity.Admin;
import com.message.inventory.model.Entity.Customer;
import com.message.inventory.repositories.AdminRepo;
import com.message.inventory.repositories.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailServiceImplementation implements UserDetailsService {
    @Autowired
    AdminRepo adminRepo;

    @Autowired
    CustomerRepo customerRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<Object[]> objectsAdmin = adminRepo.findByEmail(username);
        List<Object[]> objectsCust = customerRepo.findByEmail(username);
        if(!objectsAdmin.isEmpty()){
            Admin admin = new Admin();
            admin.setAdminId((int) objectsAdmin.get(0)[0]);
            admin.setEmail((String) objectsAdmin.get(0)[1]);
            admin.setName((String) objectsAdmin.get(0)[2]);
            admin.setPassword((String) objectsAdmin.get(0)[3]);
            admin.setPhone((String) objectsAdmin.get(0)[4]);
            admin.setWhatsappNumber((String) objectsAdmin.get(0)[5]);
//        Admin admin = new Admin((int)objectsAdmin.get(0)[0],(String)objectsAdmin.get(0)[2],(String)objectsAdmin.get(0)[1],(String)objectsAdmin.get(0)[3],(String)objectsAdmin.get(0)[4],(String)objectsAdmin.get(0)[5]);
            return new UserDetailsImplementation(admin);
        }
        else if(!objectsCust.isEmpty()){
            Customer customer = new Customer();
            customer.setCustomerId((int)objectsCust.getFirst()[0]);
            customer.setEmail((String) objectsCust.getFirst()[1]);
            customer.setName((String) objectsCust.getFirst()[2]);
            customer.setPhone((String) objectsCust.getFirst()[3]);
            customer.setPassword((String) objectsCust.getFirst()[4]);
            return new UserDetailsImplementation(customer);
        }
        else {
//            System.out.println("User not found in loadUserByUsername method.");
            throw new UsernameNotFoundException(username);
        }
    }
}
