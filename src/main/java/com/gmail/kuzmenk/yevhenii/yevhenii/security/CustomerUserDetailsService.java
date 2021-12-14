package com.gmail.kuzmenk.yevhenii.yevhenii.security;

import com.gmail.kuzmenk.yevhenii.yevhenii.service.CustomService;
import com.gmail.kuzmenk.yevhenii.yevhenii.models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomerUserDetailsService implements UserDetailsService {
    @Autowired
    CustomService customService;
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        Customer customer=customService.getUserByLogin(login);
        if (customer == null)
            throw new UsernameNotFoundException(login + " not found");

        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(customer.getRole().toString()));

        return new User(customer.getLogin(), customer.getPassword(), roles);
    }
}
