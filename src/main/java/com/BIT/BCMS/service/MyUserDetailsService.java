package com.BIT.BCMS.service;

import com.BIT.BCMS.entities.UserPrincipal;
import com.BIT.BCMS.entities.Users;
import com.BIT.BCMS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user= repo.findByUsername(username);
        if(user==null){
            System.out.println("user not found: ");
            throw new UsernameNotFoundException("User not found");
        }

        return new UserPrincipal(user);
    }

}
