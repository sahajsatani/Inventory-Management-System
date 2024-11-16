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
        if (admin != null && customer == null) return new UserDetailsImplementation(admin);
        else if (admin == null && customer != null) return new UserDetailsImplementation(customer);
        else throw new UsernameNotFoundException(username);


//        if (!admin.isEmpty() && .isEmpty()) {
////            Admin admin = Admin.builder()
////                    .adminId((int) admin.get(0)[0])
////                    .email((String) admin.get(0)[1])
////                    .name((String) admin.get(0)[2])
////                    .password((String) admin.get(0)[3])
////                    .phone((String) admin.get(0)[4])
////                    .whatsappNumber((String) admin.get(0)[5])
////                    .build();
////        Admin admin = new Admin((int)admin.get(0)[0],(String)admin.get(0)[2],(String)admin.get(0)[1],(String)admin.get(0)[3],(String)admin.get(0)[4],(String)admin.get(0)[5]);
//            return new UserDetailsImplementation(admin.);
//        }
//        else if (!.isEmpty() && admin.isEmpty()) {
//            Customer customer = Customer.builder()
//                    .customerId((int) .getFirst()[0])
//                    .email((String) .getFirst()[1])
//                    .name((String) .getFirst()[2])
//                    .phone((String) .getFirst()[3])
//                    .password((String) .getFirst()[4])
//                    .build();
//            return new UserDetailsImplementation(customer);
//        }
//        else if (!.isEmpty() && !admin.isEmpty()) {
//            Admin admin = Admin.builder()
//                    .adminId((int) admin.get(0)[0])
//                    .email((String) admin.get(0)[1])
//                    .name((String) admin.get(0)[2])
//                    .password((String) admin.get(0)[3])
//                    .phone((String) admin.get(0)[4])
//                    .whatsappNumber((String) admin.get(0)[5])
//                    .build();
////        Admin admin = new Admin((int)admin.get(0)[0],(String)admin.get(0)[2],(String)admin.get(0)[1],(String)admin.get(0)[3],(String)admin.get(0)[4],(String)admin.get(0)[5]);
//            return new UserDetailsImplementation(admin);
//        }
//        else {
//            throw new UsernameNotFoundException(username);
//        }
    }
}
