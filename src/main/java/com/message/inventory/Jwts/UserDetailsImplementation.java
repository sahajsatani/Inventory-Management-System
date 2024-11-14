package com.message.inventory.Jwts;

import com.message.inventory.model.Entity.Admin;
import com.message.inventory.model.Entity.Customer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImplementation implements UserDetails {

    private final Admin admin;
    private final Customer customer;
    public UserDetailsImplementation(Customer customer){
        this.customer = customer;
        this.admin=null;
    }

    public UserDetailsImplementation(Admin admin){
        this.admin = admin;
        this.customer=null;
    }

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
