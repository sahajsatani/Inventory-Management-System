package com.message.inventory.Jwts;

import com.message.inventory.model.Entity.Admin;
import com.message.inventory.repositories.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailServiceImplementation implements UserDetailsService {
    @Autowired
    AdminRepo adminRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<Object[]> objects = adminRepo.findByEmail(username);
        Admin admin = new Admin((int)objects.get(0)[0],(String)objects.get(0)[1],(String)objects.get(0)[2],(String)objects.get(0)[3],(String)objects.get(0)[4],(String)objects.get(0)[5]);
        if(admin==null){
            System.out.println("Not found");
            throw new UsernameNotFoundException(username);
        }
        else{
            return new UserDetailsImplementation(admin);
        }
    }
}
