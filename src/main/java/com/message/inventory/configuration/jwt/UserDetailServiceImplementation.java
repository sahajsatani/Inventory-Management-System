package com.message.inventory.configuration.jwt;

import com.message.inventory.model.entity.Admin;
import com.message.inventory.model.entity.Customer;
import com.message.inventory.repositories.AdminRepo;
import com.message.inventory.repositories.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImplementation implements UserDetailsService {
    @Autowired
    AdminRepo adminRepo;

    @Autowired
    CustomerRepo customerRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepo.findByEmail(username);
        Customer customer = customerRepo.findByEmail(username);
        if (admin != null && customer == null)
            return new UserDetailsImplementation(admin);
        else if (admin == null && customer != null)
            return new UserDetailsImplementation(customer);
        else
            throw new UsernameNotFoundException(username);
    }
}
