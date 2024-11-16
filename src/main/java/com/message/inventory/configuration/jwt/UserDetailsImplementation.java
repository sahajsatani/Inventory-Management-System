package com.message.inventory.configuration.jwt;

import com.message.inventory.model.entity.Admin;
import com.message.inventory.model.entity.Customer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
//Implement UserDetails for abstract user details and authentication process
public class UserDetailsImplementation implements UserDetails {

    private final Admin admin;
    private final Customer customer;
    //Initialise customer obj
    public UserDetailsImplementation(Customer customer){
        this.customer = customer;
        this.admin=null;
    }

    //Initialise admin obj
    public UserDetailsImplementation(Admin admin){
        this.admin = admin;
        this.customer=null;
    }

    //For retrieve authorities role of current logged in person
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(admin!=null)
            return Collections.singleton(new SimpleGrantedAuthority("ADMIN"));
        else
            return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        if(admin!=null)
            return admin.getPassword();
        else
            return customer.getPassword();
    }

    @Override
    public String getUsername() {
//        System.out.println(admin.toString());
        if(admin!=null)
            return admin.getEmail();
        else
            return customer.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
